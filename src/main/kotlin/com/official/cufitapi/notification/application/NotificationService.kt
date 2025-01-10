package com.official.cufitapi.notification.application

import com.official.cufitapi.notification.domain.Notification
import com.official.cufitapi.notification.domain.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {

    fun save(notification: Notification) {
        notificationRepository.save(notification)
    }
}