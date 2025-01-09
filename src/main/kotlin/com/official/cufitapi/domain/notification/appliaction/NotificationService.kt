package com.official.cufitapi.domain.notification.appliaction

import com.official.cufitapi.domain.notification.infrastructure.persistence.NotificationEntity
import com.official.cufitapi.domain.notification.infrastructure.persistence.NotificationJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NotificationService(
    private val notificationJpaRepository: NotificationJpaRepository
) {


    @Transactional
    fun save(
        title: String,
        content: String,
        memberId: Long,
        notificationType: NotificationType
    ) {
        notificationJpaRepository.save(
            NotificationEntity(
                title = title,
                content = content,
                memberId = memberId,
                notificationType = notificationType
            )
        )
    }
}