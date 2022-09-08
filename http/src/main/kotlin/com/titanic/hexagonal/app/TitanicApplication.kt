package com.titanic.hexagonal.app

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication
@EnableAutoConfiguration(exclude=[ DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = arrayOf("com.titanic.hexagonal.datastore", "com.titanic.hexagonal.core", "com.titanic.hexagonal.domain", "com.titanic.hexagonal.app"))
@PropertySources(
    PropertySource(value = ["classpath:/application.yml"], ignoreResourceNotFound = true),
    PropertySource(value = ["classpath:/application-datastore.yml"], ignoreResourceNotFound = true)
)
@OpenAPIDefinition(
    info = Info(
        title = "Titanic Hexagonal Ship",
        version = "0.1",
        description = "Titanic Hexagonal Ship",
        license = License(name = "Undertow", url = "http://www.emerzoom.com"),
        contact = Contact(url = "emerzoom@emerzoom.com", name = "Emerzoom", email = "emerzoom@emerzoom.com")
    )
)
class TitanicApplication

fun main(args: Array<String>) {
    runApplication<TitanicApplication>(*args)
}