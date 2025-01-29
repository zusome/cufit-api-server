package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.notification.domain.Notification
import com.official.cufitapi.domain.notification.domain.NotificationRepository
import com.official.cufitapi.domain.notification.infrastructure.persistence.NotificationEntity
import com.official.cufitapi.domain.notification.infrastructure.persistence.NotificationJpaRepository
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
                    notificationType = it.notificationType,
                    createdDate = it.createdDate!!
                )
            }
    }

    override fun save(memberId: Long, notification: Notification): Notification {
        val savedEntity = notificationJpaRepository.save(
            NotificationEntity(
                title = notification.title,
                content = notification.content,
                memberId = memberId,
                notificationType = notification.notificationType
            )
        )
        return Notification(
            savedEntity.memberId,
            savedEntity.title,
            savedEntity.content,
            savedEntity.notificationType,
            savedEntity.createdDate!!
        )
    }
}