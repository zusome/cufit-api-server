package com.official.cufitapi.common.config.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource

// @ConfigurationProperties(prefix = "service.firebase")
class FirebaseProperties(
    val resource: Resource,
    val scopes: List<String>
)