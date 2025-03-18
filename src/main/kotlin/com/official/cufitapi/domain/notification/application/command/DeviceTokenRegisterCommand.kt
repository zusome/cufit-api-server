package com.official.cufitapi.domain.notification.application.command

data class DeviceTokenRegisterCommand(
    val memberId: Long,
    val platform: String,
    val deviceToken: String
)
