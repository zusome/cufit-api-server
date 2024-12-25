package com.official.cufitapi.push.appliaction.command

data class DeviceTokenRegisterCommand(
    val memberId: Long,
    val platform: String,
    val deviceToken: String
)