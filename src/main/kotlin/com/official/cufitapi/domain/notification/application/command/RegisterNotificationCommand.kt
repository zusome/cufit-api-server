package com.official.cufitapi.domain.notification.application.command

import com.official.cufitapi.domain.notification.domain.notification.Notification

data class RegisterNotificationCommand(
    val title: String,
    val body: String,
    val inAppNotificationType: String,
    val receiverId: Long,
    val payload: String,
) {
    fun init(): Notification =
        Notification(title, body, inAppNotificationType, receiverId, payload)
}
