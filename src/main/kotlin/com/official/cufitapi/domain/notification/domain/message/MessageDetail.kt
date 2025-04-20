package com.official.cufitapi.domain.notification.domain.message

class MessageDetail(
    val to: String,
    val content: String,
    val subject: String? = null,
    val id: Long? = null
)
