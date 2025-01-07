package com.official.cufitapi.domain.notification.appliaction

import com.official.cufitapi.domain.notification.appliaction.command.DeviceTokenRegisterCommand
import com.official.cufitapi.domain.notification.domain.DeviceToken

@FunctionalInterface
fun interface DeviceTokenRegisterUseCase {
    fun registerDeviceToken(deviceTokenRegisterCommand: DeviceTokenRegisterCommand): DeviceToken
}