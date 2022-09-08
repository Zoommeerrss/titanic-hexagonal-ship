package com.titanic.hexagonal.datastore.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory

import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = arrayOf("com.titanic.hexagonal.datastore.dataprovider.repository"))
class PostgresDataSourceConfig {

    @Value("\${spring.datasource.driver-class-name}")
    private val driverClassName: String? = null

    @Value("\${spring.datasource.url}")
    private val url: String? = null

    @Value("\${spring.datasource.username}")
    private val username: String? = null

    @Value("\${spring.datasource.password}")
    private val password: String? = null

    @Value("\${spring.datasource.entityPackage}")
    private val entityPackage: String? = null

    @Bean
    @Primary
    fun dataSource(): DataSource? {

        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.driverClassName(driverClassName)
        dataSourceBuilder.url(url)
        dataSourceBuilder.username(username)
        dataSourceBuilder.password(password)
        return dataSourceBuilder.build()
    }

    @Bean
    @Primary
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val vendorAdapter = HibernateJpaVendorAdapter()
        vendorAdapter.setGenerateDdl(true)
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan(entityPackage)
        factory.dataSource = dataSource()!!
        return factory
    }

    @Bean
    @Primary
    fun transactionManager(entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager? {
        val txManager = JpaTransactionManager()
        txManager.entityManagerFactory = entityManagerFactory
        return txManager
    }
}