package com.official.cufitapi.domain.notification.application

import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.domain.InAppNotification
import com.official.cufitapi.domain.notification.domain.InAppNotificationRepository
import org.springframework.stereotype.Service


interface RegisterInAppNotificationUseCase {
    fun register(registerInAppNotification: RegisterInAppNotificationCommand): InAppNotification
}

@Service
class InAppNotificationService(
    private val inAppNotificationRepository: InAppNotificationRepository
): RegisterInAppNotificationUseCase {

    override fun register(command: RegisterInAppNotificationCommand): InAppNotification {
        return inAppNotificationRepository.save(command.init())
    }
}
