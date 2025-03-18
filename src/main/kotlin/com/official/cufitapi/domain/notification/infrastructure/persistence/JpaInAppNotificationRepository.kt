package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaInAppNotificationRepository: JpaRepository<JpaInAppNotification, Long> {
}
