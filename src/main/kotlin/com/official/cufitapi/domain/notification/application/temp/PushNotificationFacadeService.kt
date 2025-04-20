package com.official.cufitapi.domain.notification.application.temp

import org.springframework.stereotype.Service

@Service
class PushNotificationFacadeService(
    private val registerPushNotificationUseCase: RegisterPushNotificationUseCase,
) {

    fun sendInvitationPushNotification(userId: String, token: String) {
        val registerPushNotificationCommand = RegisterPushNotificationCommand(userId, token)
        registerPushNotificationUseCase.register(registerPushNotificationCommand)
    }
}
