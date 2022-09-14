import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

configurations.all() {
    exclude("org.springframework.boot", "spring-boot-starter-tomcat")
}

plugins {
    id("application")
    id("org.springframework.boot")
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("jvm")
    kotlin("plugin.spring")
    jacoco
}

group = "titanic.app"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    // Open Api
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.0")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.0")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.0")

    // Others
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.hibernate.validator:hibernate-validator")
    implementation("javax.validation:validation-api")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // projects
    implementation(project(":core"))
    implementation(project(":domain"))
}

apply(plugin = "io.spring.dependency-management")
apply(plugin = "jacoco")

tasks.test {
    useJUnitPlatform()

    finalizedBy(":http:jacocoTestReport")
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}
