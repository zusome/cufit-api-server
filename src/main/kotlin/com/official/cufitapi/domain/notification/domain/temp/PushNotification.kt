package com.official.cufitapi.domain.notification.domain.temp

import java.time.LocalDateTime

class PushNotification(
    private val token: String,
    private val title: String,
    private val body: String,
    private val metaData: String,
    private val platform: String,
    private val memberId: Long,
    private val notificationAt: LocalDateTime? = null,
    private val pushNotificationId: Long? = null,
) {

}
