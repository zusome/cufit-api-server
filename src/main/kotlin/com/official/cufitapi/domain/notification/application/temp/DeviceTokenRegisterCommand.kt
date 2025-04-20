package com.official.cufitapi.domain.notification.application.temp

data class DeviceTokenRegisterCommand(
    val memberId: Long,
    val platform: String,
    val deviceToken: String
)
