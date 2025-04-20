package com.official.cufitapi.domain.notification.domain.temp

interface NotificationLegacyRepository {
    fun findAllByMemberId(memberId: Long): List<NotificationLegacy>
    fun save(notificationLegacy: NotificationLegacy): NotificationLegacy
}
