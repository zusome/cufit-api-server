package com.official.cufitapi.domain.notification.domain.temp

import com.official.cufitapi.domain.notification.domain.Notification

interface NotificationRepository {
    fun findAllByMemberId(memberId: Long): List<Notification>
    fun save(notification: Notification): Notification
}
