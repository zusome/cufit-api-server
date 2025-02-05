package com.official.cufitapi.domain.notification.domain

interface NotificationRepository {
    fun findAllByMemberId(memberId: Long): List<Notification>
    fun save(notification: Notification): Notification
}