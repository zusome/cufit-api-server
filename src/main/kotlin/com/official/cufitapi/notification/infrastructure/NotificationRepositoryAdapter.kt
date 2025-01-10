package com.official.cufitapi.notification.infrastructure

import com.official.cufitapi.notification.infrastructure.persistence.NotificationEntity
import com.official.cufitapi.notification.infrastructure.persistence.NotificationJpaRepository
import com.official.cufitapi.notification.domain.NotificationRepository
import com.official.cufitapi.notification.domain.Notification
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
open class NotificationRepositoryAdapter(
    private val notificationJpaRepository: NotificationJpaRepository
) : NotificationRepository{

    @Transactional(readOnly = true)
    override fun findAll(memberId: Long) : List<Notification> {
        return notificationJpaRepository.findAllByMemberIdOrderByCreatedDateDesc(memberId)
            .map {
                Notification(
                    id = it.id!!,
                    title = it.title,
                    content = it.content,
                    memberId = it.memberId,
                    type = it.notificationType,
                    createdDate = it.createdDate!!
                )
            }
    }

    @Transactional
    override fun save(notification: Notification) {
        notificationJpaRepository.save(
            NotificationEntity(
                title = notification.title,
                content = notification.content,
                memberId = notification.memberId,
                notificationType = notification.type
            )
        )
    }
}