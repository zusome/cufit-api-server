package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.domain.temp.DeviceToken

@FunctionalInterface
fun interface DeviceTokenRegisterUseCase {
    fun registerDeviceToken(deviceTokenRegisterCommand: DeviceTokenRegisterCommand): DeviceToken
}
