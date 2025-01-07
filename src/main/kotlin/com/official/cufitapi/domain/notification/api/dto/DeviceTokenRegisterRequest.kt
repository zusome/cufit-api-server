package com.official.cufitapi.domain.notification.api.dto

data class DeviceTokenRegisterRequest(
    val platform: String,
    val deviceToken: String
)
