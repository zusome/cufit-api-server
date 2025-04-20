package com.official.cufitapi.common.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile


// @ConfigurationProperties(prefix = "service.sens")
data class SensProperties(
    val hostUrl: String,
    val serviceId: String,
    val accessKey: String,
    val apiGateWaySignature: String,
    val secretKey: String
) {

}
