package com.official.cufitapi.domain.notification.domain

import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import java.time.LocalDateTime

class Notification(
    val id: Long,
    val title: String,
    val content: String,
    val inAppNotificationType: InAppNotificationType,
    val createdDate: LocalDateTime,
    val memberId: Long
) {
}
