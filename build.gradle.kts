
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//in root project configuration
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.7.4")
    }
}

plugins {
    kotlin("jvm")
    id("info.solidsoft.pitest") version "1.7.4"
    jacoco
}

group = "titanic.main"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

apply(plugin = "info.solidsoft.pitest.aggregator")
apply(plugin = "jacoco")

allprojects {

    println("Project running ${project.name} from ${project.buildDir}")
    apply(plugin = "java")
    apply(plugin = "info.solidsoft.pitest")
    apply(plugin = "jacoco")

    repositories {
        mavenCentral()
    }

    dependencies {

        // Others
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation(kotlin("script-runtime"))

        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")

        // pitest
        implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.7.4")

        //MockK
        testImplementation("io.mockk:mockk:1.9.3")

        //junit 5
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    }

    tasks.test {

        useJUnitPlatform()

        dependsOn(":datastore:pitest")
        dependsOn(":core:pitest")
        dependsOn(":domain:pitest")
        dependsOn(":http:pitest")

        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(layout.buildDirectory.file("jacoco/test.exec").get().asFile)
            classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
        }

        finalizedBy( ":moveReports", ":pitestReportAggregate", ":jacocoTestReport")
    }

    tasks.jacocoTestReport {

        additionalSourceDirs.setFrom(files(sourceSets.main.get().allSource.srcDirs))
        sourceDirectories.setFrom(files(sourceSets.main.get().allSource.srcDirs))
        classDirectories.setFrom(files(sourceSets.main.get().output))

        reports {
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.dir("/reports/jacoco/html"))
            xml.required.set(true)
            xml.outputLocation.set(file("${buildDir}/reports/jacoco/jacoco.xml"))
            csv.required.set(false)
        }

        dependsOn(tasks.test)
    }

    tasks.create<Copy>("moveReports") {

        from(project(":http").buildDir) {
            include("reports/pitest/**")
        }
        into("${rootProject.projectDir}/build/")

    }

    tasks.withType<KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    pitest {
        targetClasses.add("com.titanic.hexagonal.*")
        targetTests.add("com.titanic.hexagonal.*Test")
        testSourceSets.add(sourceSets.test)
        mainSourceSets.add(sourceSets.main)
        jvmArgs.addAll("-Xmx1024m", "-Dspring.test.constructor.autowire.mode=all")
        useClasspathFile.set(true)     //useful with bigger projects on Windows
        fileExtensionsToFilter.addAll("xml", "orbit")
        outputFormats.addAll("XML")
        timestampedReports.set(false)
        junit5PluginVersion.set("0.15")
        exportLineCoverage.set(true)

    }
}

jacoco {

    toolVersion = "0.8.7"
}

tasks.test {

    useJUnitPlatform()

    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(layout.buildDirectory.file("jacoco/test.exec").get().asFile)
        classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
    }

    finalizedBy( ":jacocoRootReport")
}

tasks.create<JacocoReport>("jacocoRootReport") {

    subprojects {
        val subproject = this
        subproject.plugins.withType<JacocoPlugin>().configureEach {
            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                val testTask = this
                sourceSets(this@subprojects.the<SourceSetContainer>().named("main").get())
                executionData(testTask)
            }
            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
                rootProject.tasks["jacocoTestReport"].dependsOn(it)
            }
        }
    }

    reports {
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("/reports/jacoco/html"))
        xml.required.set(true)
        xml.outputLocation.set(file("${buildDir}/reports/jacoco/jacoco.xml"))
        csv.required.set(false)
    }

    dependsOn(tasks.jacocoTestReport)
}