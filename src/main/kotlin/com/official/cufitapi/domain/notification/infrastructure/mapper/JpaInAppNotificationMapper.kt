package com.official.cufitapi.domain.notification.infrastructure.mapper

import com.official.cufitapi.domain.notification.domain.notification.Notification
import com.official.cufitapi.domain.notification.infrastructure.persistence.JpaNotification
import org.springframework.stereotype.Component

@Component
class JpaInAppNotificationMapper {
    fun mapToEntity(notification: Notification): JpaNotification {
        return JpaNotification(
            title = notification.title,
            content = notification.content,
            notificationType = notification.notificationType.name,
            receiverId = notification.receiverId,
            payload = notification.payload,
            id = notification.id,
        )
    }

    fun mapToDomain(jpaNotification: JpaNotification): Notification {
        return Notification(
            title = jpaNotification.title,
            content = jpaNotification.content,
            alarmType = jpaNotification.notificationType,
            receiverId = jpaNotification.receiverId,
            payload = jpaNotification.payload,
            id = jpaNotification.id,
        )
    }
}
