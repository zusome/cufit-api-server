package com.official.cufitapi.acceptance

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLTestContainer : Extension, BeforeAllCallback {

    override fun beforeAll(extensionContext: ExtensionContext?) {
        if (POSTGRE_SQL_CONTAINER.isRunning()) {
            return
        }
        POSTGRE_SQL_CONTAINER.start()
        System.setProperty("spring.datasource.url", POSTGRE_SQL_CONTAINER.jdbcUrl)
        System.setProperty("spring.datasource.username", POSTGRE_SQL_CONTAINER.username)
        System.setProperty("spring.datasource.password", POSTGRE_SQL_CONTAINER.password)
        System.setProperty("spring.datasource.driver-class-name", POSTGRE_SQL_CONTAINER.driverClassName)
    }

    companion object {
        private val DOCKER_IMAGE: String = "postgres:17.4"
        private val POSTGRE_SQL_CONTAINER: PostgreSQLContainer<*> = PostgreSQLContainer(DOCKER_IMAGE)
            .withDatabaseName("shopping")
            .withUsername("root")
            .withPassword("1234")
    }
}
