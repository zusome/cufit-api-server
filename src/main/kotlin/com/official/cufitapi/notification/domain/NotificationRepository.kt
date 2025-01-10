package com.official.cufitapi.notification.domain

interface NotificationRepository {
    fun findAll(memberId: Long) : List<Notification>
    fun save(notification: Notification)
}