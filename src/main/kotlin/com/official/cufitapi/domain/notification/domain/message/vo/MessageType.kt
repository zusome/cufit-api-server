package com.official.cufitapi.domain.notification.domain.message.vo

enum class MessageType(val value: String) {
    SMS("SMS"),
    LMS("LMS"),
    MMS("MMS");

    companion object {
        fun of(type: String): MessageType = entries.firstOrNull { it.value == type }
            ?: throw IllegalArgumentException("Invalid message type: $type")
    }
}
