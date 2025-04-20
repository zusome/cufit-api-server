package com.official.cufitapi.domain.notification.domain.temp.event

import com.official.cufitapi.domain.notification.domain.temp.NotificationLegacy
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class NotificationEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    fun publishNotificationSavedEvent(notificationLegacy: NotificationLegacy) {
        val event = NotificationSavedEvent(this, notificationLegacy)
        applicationEventPublisher.publishEvent(event)
    }
}

class NotificationSavedEvent (
    source: Any,
    val notificationLegacy: NotificationLegacy
) : ApplicationEvent(source)
