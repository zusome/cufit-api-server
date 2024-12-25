package com.official.cufitapi.push.api.dto

data class DeviceTokenRegisterRequest(
    val platform: String,
    val deviceToken: String
)
