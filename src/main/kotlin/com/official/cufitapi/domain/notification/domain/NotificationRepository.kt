package com.official.cufitapi.domain.notification.domain

interface NotificationRepository {
    fun findAllByMemberId(memberId: Long): List<Notification>
    fun save(memberId: Long, notification: Notification): Notification
}