package com.official.cufitapi.domain.notification.domain

import com.official.cufitapi.domain.notification.appliaction.NotificationType
import java.time.LocalDateTime

class Notification(
    val id: Long,
    val title: String,
    val content: String,
    val notificationType: NotificationType,
    val createdDate: LocalDateTime,
    val memberId: Long
) {
}