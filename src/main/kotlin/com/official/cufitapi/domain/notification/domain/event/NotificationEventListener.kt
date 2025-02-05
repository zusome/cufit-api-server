package com.official.cufitapi.domain.notification.domain.event

import com.official.cufitapi.domain.notification.domain.NotificationRepository
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val notificationRepository: NotificationRepository
) {

    @EventListener
    fun handleNotificationSavedEvent(event: NotificationSavedEvent) {
        notificationRepository.save(event.notification)
    }
}