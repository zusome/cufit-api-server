package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.notification.domain.InAppNotification
import com.official.cufitapi.domain.notification.domain.InAppNotificationRepository
import com.official.cufitapi.domain.notification.infrastructure.mapper.JpaInAppNotificationMapper
import com.official.cufitapi.domain.notification.infrastructure.persistence.JpaInAppNotificationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultInAppNotificationRepository(
    private val jpaInAppNotificationMapper: JpaInAppNotificationMapper,
    private val jpaInAppNotificationRepository: JpaInAppNotificationRepository
): InAppNotificationRepository {

    @Transactional(readOnly = false)
    override fun save(inAppNotification: InAppNotification): InAppNotification {
        val entity = jpaInAppNotificationMapper.mapToEntity(inAppNotification)
        return jpaInAppNotificationRepository.save(entity).let(jpaInAppNotificationMapper::mapToDomain)
    }
}
