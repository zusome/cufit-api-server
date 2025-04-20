package com.official.cufitapi.domain.notification.domain.message.vo

enum class MessageContentType(val value: String) {
    COMM("COMM"),
    AD("AD");

    companion object {
        fun of(contentType: String): MessageContentType = entries.firstOrNull { it.value == contentType }
            ?: throw IllegalArgumentException("Invalid message content type: $contentType")
    }
}
