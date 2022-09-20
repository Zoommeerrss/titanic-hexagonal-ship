import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    id("org.springframework.boot")
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    jacoco
}

group = "titanic.datastore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // postgres
    implementation("org.postgresql:postgresql:42.5.0")

    implementation("org.hibernate:hibernate-core:6.1.2.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // projects
    implementation(project(":core"))
}

apply(plugin = "io.spring.dependency-management")
apply(plugin = "jacoco")

tasks.test {
    useJUnitPlatform()

    finalizedBy(":datastore:jacocoTestReport")
}


the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}


allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
