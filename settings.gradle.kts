pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.gradle.sample") {
                useModule("com.gradle:sample-plugins:1.0.0")
            }
            // springboot
            if (requested.id.id.startsWith("org.springframework.boot")) {
                useVersion("2.7.1")
            }
            if (requested.id.id.startsWith("io.spring.dependency-management")) {
                useVersion("1.0.12.RELEASE")
            }
            // kotlin
            if (requested.id.id.startsWith("org.jetbrains.kotlin.")) {
                useVersion("1.6.21")
            }
        }
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "titanic-hexagonal-ship"

include("http")
include("datastore")
include("domain")
include("core")
