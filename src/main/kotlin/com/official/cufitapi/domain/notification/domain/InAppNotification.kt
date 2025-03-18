package com.official.cufitapi.domain.notification.domain

import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType

class InAppNotification(
    val title: String,
    val content: String,
    val inAppNotificationType: InAppNotificationType,
    val receiverId: Long,
    val payload: String,
    val id: Long? = null,
) {
    constructor(title: String, content: String, inAppNotificationType: String, receiverId: Long, payload: String, id: Long? = null) : this(
        title,
        content,
        InAppNotificationType.valueOf(inAppNotificationType),
        receiverId,
        payload,
        id
    )
}
