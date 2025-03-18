package com.official.cufitapi.domain.notification.application.command

import com.official.cufitapi.domain.notification.domain.InAppNotification

data class RegisterInAppNotificationCommand(
    val title: String,
    val body: String,
    val inAppNotificationType: String,
    val receiverId: Long,
    val payload: String,
) {
    fun init(): InAppNotification =
        InAppNotification(title, body, inAppNotificationType, receiverId, payload)
}
