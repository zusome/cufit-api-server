package com.official.cufitapi.domain.notification.domain.temp

import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType
import java.time.LocalDateTime

class NotificationLegacy(
    val id: Long,
    val title: String,
    val content: String,
    val notificationType: NotificationType,
    val createdDate: LocalDateTime,
    val memberId: Long
) {
}
