package com.official.cufitapi.domain.notification.domain.message

interface MessageRepository {
    fun save(message: Message): Message
}
