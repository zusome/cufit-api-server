package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.notification.domain.notification.Notification
import com.official.cufitapi.domain.notification.domain.notification.NotificationRepository
import com.official.cufitapi.domain.notification.infrastructure.mapper.JpaInAppNotificationMapper
import com.official.cufitapi.domain.notification.infrastructure.persistence.JpaNotificationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultNotificationRepository(
    private val jpaInAppNotificationMapper: JpaInAppNotificationMapper,
    private val jpaNotificationRepository: JpaNotificationRepository
): NotificationRepository {

    @Transactional(readOnly = false)
    override fun save(notification: Notification): Notification {
        val entity = jpaInAppNotificationMapper.mapToEntity(notification)
        return jpaNotificationRepository.save(entity).let(jpaInAppNotificationMapper::mapToDomain)
    }
}
