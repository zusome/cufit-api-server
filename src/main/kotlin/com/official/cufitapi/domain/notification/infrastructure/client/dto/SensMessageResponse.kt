package com.official.cufitapi.domain.notification.infrastructure.client.dto

data class SensMessageResponse(
    val requestId: String,
    val requestTime: String,
    val statusCode: String,
    val statusName: String,
)
