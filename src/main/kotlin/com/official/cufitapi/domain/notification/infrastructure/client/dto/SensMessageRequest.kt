package com.official.cufitapi.domain.notification.infrastructure.client.dto

data class SensMessageRequest(
    val from: String,
    val content: String,
    val messages: List<SensMessage>,
    val type: String = "SMS",
    val contentType: String = "COMM",
    val countryCode: String = "82",
    val files: List<SensMessageFile>? = null,
    val subject: String? = null,
    val reserveTime: String? = null,
    val reserveTimeZone: String? = null,
)
