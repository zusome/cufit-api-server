package com.official.cufitapi.domain.notification.domain.notification

import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType

class Notification(
    val title: String,
    val content: String,
    val notificationType: NotificationType,
    val receiverId: Long,
    val payload: String,
    val id: Long? = null,
) {
    constructor(title: String, content: String, alarmType: String, receiverId: Long, payload: String, id: Long? = null) : this(
        title,
        content,
        NotificationType.valueOf(alarmType),
        receiverId,
        payload,
        id
    )
}
