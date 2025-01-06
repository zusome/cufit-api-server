package com.official.cufitapi.domain.notification.appliaction.command

data class DeviceTokenRegisterCommand(
    val memberId: Long,
    val platform: String,
    val deviceToken: String
)