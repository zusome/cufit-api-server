package com.official.cufitapi.common.config.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service.authorization")
data class AuthorizationProperties(
    val secretKey: String,
    val accessTimeOut: Long,
    val refreshTimeOut: Long,
)