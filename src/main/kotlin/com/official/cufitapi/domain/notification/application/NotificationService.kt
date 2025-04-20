package com.official.cufitapi.domain.notification.application

import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import com.official.cufitapi.domain.notification.domain.notification.Notification
import com.official.cufitapi.domain.notification.domain.notification.NotificationRepository
import org.springframework.stereotype.Service


interface RegisterNotificationUseCase {
    fun register(registerInAppNotification: RegisterNotificationCommand): Notification
}

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
): RegisterNotificationUseCase {

    override fun register(command: RegisterNotificationCommand): Notification {
        return notificationRepository.save(command.init())
    }
}
