package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.api.dto.notification.NotificationResponse
import com.official.cufitapi.domain.enums.NotificationType
import com.official.cufitapi.domain.infrastructure.entity.Notification
import com.official.cufitapi.domain.infrastructure.repository.NotificationJpaRepository
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
            Notification(
                title = title,
                content = content,
                memberId = memberId,
                notificationType = notificationType
            )
        )
    }
}