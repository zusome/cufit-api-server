package com.official.cufitapi.notification.domain

import com.official.cufitapi.domain.enums.NotificationType
import java.time.LocalDateTime

class Notification(
    val id: Long,
    val title: String,
    val content: String,
    val type: NotificationType,
    val memberId: Long,
    val createdDate: LocalDateTime
) {
}