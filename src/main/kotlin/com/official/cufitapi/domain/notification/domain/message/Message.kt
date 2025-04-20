package com.official.cufitapi.domain.notification.domain.message

import com.official.cufitapi.domain.notification.domain.message.vo.MessageContentType
import com.official.cufitapi.domain.notification.domain.message.vo.MessageCountryCode
import com.official.cufitapi.domain.notification.domain.message.vo.MessageFiles
import com.official.cufitapi.domain.notification.domain.message.vo.MessageType
import com.official.cufitapi.domain.notification.domain.message.vo.MessageReserveTime

class Message(
    val from: String,
    val content: String,
    val messages: MessageDetails,
    val type: MessageType = MessageType.SMS,
    val contentType: MessageContentType = MessageContentType.COMM,
    val countryCode: MessageCountryCode = MessageCountryCode.KOR,
    val subject: String? = null,
    val files: MessageFiles? = null,
    val messageReserveTime: MessageReserveTime? = null,
    val id: Long? = null,
) {
    init {
        if (MessageType.SMS == type && subject?.isNotBlank() == true) {
            throw IllegalArgumentException("SMS type does not support subject.")
        }
        if (MessageType.SMS == type && content.length > 90) {
            throw IllegalArgumentException("SMS type content size exceeds 90 bytes.")
        }
        if (MessageType.LMS == type && content.length > 2000) {
            throw IllegalArgumentException("LMS type content size exceeds 2000 bytes.")
        }
    }
}
