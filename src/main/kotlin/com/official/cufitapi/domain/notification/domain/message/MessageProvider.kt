package com.official.cufitapi.domain.notification.domain.message

interface MessageProvider {
    fun send(message: Message): String
}
