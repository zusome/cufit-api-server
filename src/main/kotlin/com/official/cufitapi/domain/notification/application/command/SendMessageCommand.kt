package com.official.cufitapi.domain.notification.application.command

data class SendMessageCommand(
    val senderId: Long,
    val receiverId: Long,
    val message: String,
    val type: String,
    val title: String,
    val imageUrl: String? = null,
    val linkUrl: String? = null,
) {
}
