
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
}

group = "titanic.main"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

apply(plugin = "info.solidsoft.pitest.aggregator")

allprojects {

    apply(plugin = "java")
    apply(plugin = "info.solidsoft.pitest")

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

    tasks.test {
        useJUnitPlatform()

        dependsOn(":http:pitest")
        dependsOn(":domain:pitest")
        dependsOn(":datastore:pitest")
        dependsOn(":core:pitest")
        dependsOn(":moveReports")
    }

    tasks.create<Copy>("moveReports") {

//        from("${project(":http")}/build/reports")
//        to("${rootProject.projectDir}/build/reports")

        from(layout.buildDirectory) {
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
