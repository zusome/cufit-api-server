package com.official.cufitapi.domain.notification.infrastructure.client.dto

data class SensMessage(
    val to: String,
    val content: String,
    val subject: String? = null,
)


