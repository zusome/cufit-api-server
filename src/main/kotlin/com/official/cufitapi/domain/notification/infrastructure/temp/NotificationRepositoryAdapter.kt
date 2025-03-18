package com.official.cufitapi.domain.notification.infrastructure.temp

import com.official.cufitapi.domain.notification.domain.Notification
import com.official.cufitapi.domain.notification.domain.temp.NotificationRepository
import com.official.cufitapi.domain.notification.infrastructure.persistence.temp.NotificationEntity
import com.official.cufitapi.domain.notification.infrastructure.persistence.temp.NotificationJpaRepository
import org.springframework.stereotype.Component

@Component
class NotificationRepositoryAdapter(
    private val notificationJpaRepository: NotificationJpaRepository
) : NotificationRepository {
    override fun findAllByMemberId(memberId: Long): List<Notification> {
        return notificationJpaRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId)
            .map {
                Notification(
                    id = it.id!!,
                    title = it.title,
                    content = it.content,
                    inAppNotificationType = it.inAppNotificationType,
                    createdDate = it.createdDate!!,
                    memberId = it.memberId
                )
            }
    }

    override fun save(notification: Notification): Notification {
        val savedEntity = notificationJpaRepository.save(
            NotificationEntity(
                title = notification.title,
                content = notification.content,
                memberId = notification.memberId,
                inAppNotificationType = notification.inAppNotificationType
            )
        )
        return Notification(
            savedEntity.memberId,
            savedEntity.title,
            savedEntity.content,
            savedEntity.inAppNotificationType,
            savedEntity.createdDate!!,
            savedEntity.memberId
        )
    }
}
