package com.official.cufitapi.common.config.property

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "coolsms")
data class CoolsmsProperty(
    val url: String,
    val from: String,
    val apiKey: String,
    val secretKey: String,
) {
    init {
        logger.info("url: $url")
        logger.info("from: $from")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CoolsmsProperty::class.java)
    }
}
