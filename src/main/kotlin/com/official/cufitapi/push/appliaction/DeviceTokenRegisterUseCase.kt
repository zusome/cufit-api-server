package com.official.cufitapi.push.appliaction

import com.official.cufitapi.push.appliaction.command.DeviceTokenRegisterCommand
import com.official.cufitapi.push.domain.DeviceToken

@FunctionalInterface
fun interface DeviceTokenRegisterUseCase {
    fun registerDeviceToken(deviceTokenRegisterCommand: DeviceTokenRegisterCommand): DeviceToken
}