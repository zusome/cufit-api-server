package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.domain.temp.PushNotification
import com.official.cufitapi.domain.notification.domain.temp.PushNotificationRepository
import org.springframework.stereotype.Service

interface RegisterPushNotificationUseCase {
    fun register(registerPushNotificationCommand: RegisterPushNotificationCommand): PushNotification
}

@Service
class PushNotificationService(
    private val pushNotificationRepository: PushNotificationRepository,
) : RegisterPushNotificationUseCase {

    override fun register(registerPushNotificationCommand: RegisterPushNotificationCommand): PushNotification {
        // todo
        return PushNotification(
            "", "", "", "", "", 0L
        )
    }
}
