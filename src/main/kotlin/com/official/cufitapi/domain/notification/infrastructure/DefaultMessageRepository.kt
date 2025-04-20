package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.notification.domain.message.Message
import com.official.cufitapi.domain.notification.domain.message.MessageRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMessageRepository(

): MessageRepository {

    @Transactional
    override fun save(message: Message): Message {
        return message
    }
}
