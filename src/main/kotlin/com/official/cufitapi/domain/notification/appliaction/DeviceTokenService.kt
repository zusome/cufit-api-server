package com.official.cufitapi.domain.notification.appliaction

import com.official.cufitapi.domain.notification.appliaction.command.DeviceTokenRegisterCommand
import com.official.cufitapi.domain.notification.domain.DeviceToken
import com.official.cufitapi.domain.notification.domain.DeviceTokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class DeviceTokenService(
    private val deviceTokenRepository: DeviceTokenRepository
) : DeviceTokenRegisterUseCase {

    override fun registerDeviceToken(deviceTokenRegisterCommand: DeviceTokenRegisterCommand): DeviceToken =
        deviceTokenRepository.save(init(deviceTokenRegisterCommand))

    private fun init(deviceTokenRegisterCommand: DeviceTokenRegisterCommand): DeviceToken {
        val createdAt = LocalDateTime.now()
        return DeviceToken(
            memberId = deviceTokenRegisterCommand.memberId,
            platform = deviceTokenRegisterCommand.platform,
            deviceToken = deviceTokenRegisterCommand.deviceToken,
            createdAt = createdAt,
            updatedAt = createdAt
        )
    }
}