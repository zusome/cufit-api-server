package com.official.cufitapi.domain.notification.domain.notification

interface NotificationRepository {
    fun save(notification: Notification): Notification
}
