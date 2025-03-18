package com.official.cufitapi.domain.notification.infrastructure.mapper

import com.official.cufitapi.domain.notification.domain.InAppNotification
import com.official.cufitapi.domain.notification.infrastructure.persistence.JpaInAppNotification
import org.springframework.stereotype.Component

@Component
class JpaInAppNotificationMapper {
    fun mapToEntity(inAppNotification: InAppNotification): JpaInAppNotification {
        return JpaInAppNotification(
            title = inAppNotification.title,
            content = inAppNotification.content,
            notificationType = inAppNotification.inAppNotificationType.name,
            receiverId = inAppNotification.receiverId,
            payload = inAppNotification.payload,
            id = inAppNotification.id,
        )
    }

    fun mapToDomain(jpaInAppNotification: JpaInAppNotification): InAppNotification {
        return InAppNotification(
            title = jpaInAppNotification.title,
            content = jpaInAppNotification.content,
            inAppNotificationType = jpaInAppNotification.notificationType,
            receiverId = jpaInAppNotification.receiverId,
            payload = jpaInAppNotification.payload,
            id = jpaInAppNotification.id,
        )
    }
}
