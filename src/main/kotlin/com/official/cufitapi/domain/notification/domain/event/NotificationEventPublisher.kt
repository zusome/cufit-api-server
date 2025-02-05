package com.official.cufitapi.domain.notification.domain.event

import com.official.cufitapi.domain.notification.domain.Notification
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class NotificationEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun publishNotificationSavedEvent(notification: Notification) {
        val event = NotificationSavedEvent(this, notification)
        applicationEventPublisher.publishEvent(event)
    }
}

class NotificationSavedEvent (
    source: Any,
    val notification: Notification
) : ApplicationEvent(source)