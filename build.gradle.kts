
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

    repositories {
        mavenCentral()
    }

    dependencies {

        // Others
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation(kotlin("script-runtime"))

        // pitest
        implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.7.4")

        //MockK
        testImplementation("io.mockk:mockk:1.9.3")

        //junit 5
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    }

    tasks.withType<KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.register<Copy>("movePitestMutations") {

        dependsOn(":datastore:pitest")
        dependsOn(":domain:pitest")
        dependsOn(":http:pitest")

        from(rootProject.projectDir.path) {
            include("reports/pitest/**")
        }
        into("${rootProject.projectDir}/build/")

        finalizedBy(":inspectClassesForKotlinIC", ":pitestReportAggregate")
    }

    tasks.register<Copy>("moveCoreNoPitestMutations") {

        dependsOn(":datastore:pitest")
        dependsOn(":domain:pitest")
        dependsOn(":http:pitest")

        from(rootProject.projectDir.path) {
            include("reports/pitest/**")
        }
        into("${project(":core").projectDir}/build/")

        finalizedBy(
            ":inspectClassesForKotlinIC",
            ":core:bootJarMainClassName",
            ":domain:bootJarMainClassName",
            ":http:bootJarMainClassName",
            ":http:distTar",
            ":http:distZip",
            ":http:startScripts",
            ":datastore:bootJarMainClassName",
            ":pitestReportAggregate"
        )
    }

}

subprojects {

    apply(plugin = "info.solidsoft.pitest")
    apply(plugin = "jacoco")

    tasks.test {

        useJUnitPlatform()

        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(layout.buildDirectory.file("jacoco/test.exec").get().asFile)
            classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
        }

        finalizedBy(":jacocoTestReport")
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

    pitest {

        // datastore
        targetClasses.add("com.titanic.hexagonal.datastore.component.*")
        targetTests.add("com.titanic.hexagonal.datastore.component.*Test")

        // domain
        targetClasses.add("com.titanic.hexagonal.domain.*")
        targetTests.add("com.titanic.hexagonal.domain.*Test")

        // http
        targetClasses.add("com.titanic.hexagonal.app.entrypoint.*")
        targetTests.add("com.titanic.hexagonal.app.entrypoint.*Test")

        testSourceSets.add(sourceSets.test)
        mainSourceSets.add(sourceSets.main)
        jvmArgs.addAll("-Xmx1024m", "-Dspring.test.constructor.autowire.mode=all")
        useClasspathFile.set(true)     //useful with bigger projects on Windows
        fileExtensionsToFilter.addAll("xml", "orbit")
        outputFormats.addAll("XML")
        timestampedReports.set(false)
        junit5PluginVersion.set("0.15")
        exportLineCoverage.set(true)
        failWhenNoMutations.set(false)
        avoidCallsTo.add("com.titanic.hexagonal.core")
    }

}

tasks.test {

    useJUnitPlatform()

    extensions.configure(JacocoTaskExtension::class) {
        setDestinationFile(layout.buildDirectory.file("jacoco/test.exec").get().asFile)
        classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
    }

    finalizedBy(":jacocoTestReport", ":jacocoRootReport", ":movePitestMutations", ":moveCoreNoPitestMutations", ":pitestReportAggregate")
}

jacoco {

    toolVersion = "0.8.7"
}

tasks.create<JacocoReport>("jacocoRootReport") {

    subprojects {
        val subproject = this

        if(!subproject.name.equals("core")) {
            subproject.plugins.withType<JacocoPlugin>().configureEach {
                subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                    val testTask = this
                    sourceSets(this@subprojects.the<SourceSetContainer>().named("test").get())
                    executionData(testTask)
                }
                subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
                    rootProject.tasks["jacocoTestReport"].dependsOn(it)
                }
            }
        }
    }

    dependsOn(tasks.jacocoTestReport)
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
}

