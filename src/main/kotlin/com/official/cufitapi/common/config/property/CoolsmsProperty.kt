package com.official.cufitapi.common.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "coolsms")
data class CoolsmsProperty(
    val url: String,
    val from: String,
    val apiKey: String,
    val secretKey: String,
) {
}
