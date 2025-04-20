package com.official.cufitapi.domain.notification.application.temp

data class RegisterPushNotificationCommand(
    val userId: String,
    val token: String
) {
}
