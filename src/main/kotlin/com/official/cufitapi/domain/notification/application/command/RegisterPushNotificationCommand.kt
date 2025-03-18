package com.official.cufitapi.domain.notification.application.command

data class RegisterPushNotificationCommand(
    val userId: String,
    val token: String
) {
}
