package com.official.cufitapi.domain.notification.application

import com.official.cufitapi.domain.notification.application.command.RegisterMessageCommand
import com.official.cufitapi.domain.notification.domain.message.Message
import com.official.cufitapi.domain.notification.domain.message.MessageDetail
import com.official.cufitapi.domain.notification.domain.message.MessageDetails
import com.official.cufitapi.domain.notification.domain.message.MessageOwner
import com.official.cufitapi.domain.notification.domain.message.MessageRepository
import com.official.cufitapi.domain.notification.domain.message.vo.MessageContentType
import com.official.cufitapi.domain.notification.domain.message.vo.MessageCountryCode
import com.official.cufitapi.domain.notification.domain.message.vo.MessageFileId
import com.official.cufitapi.domain.notification.domain.message.vo.MessageFiles
import com.official.cufitapi.domain.notification.domain.message.vo.MessageReserveTime
import com.official.cufitapi.domain.notification.domain.message.vo.MessageType
import org.springframework.stereotype.Component

interface SendMessageUseCase {
    fun send(command: RegisterMessageCommand): Message
}

@Component
class MessageService(
    private val messageRepository: MessageRepository,
) : SendMessageUseCase {

    private val messageOwner: MessageOwner = MessageOwner { "111-0000-0000" }

    override fun send(command: RegisterMessageCommand): Message {
        return messageRepository.save(init(command))
    }

    private fun init(command: RegisterMessageCommand) =
        Message(
            from = messageOwner.from(),
            content = command.content,
            messages = command.messages.map { MessageDetail(it.to, it.content, it.subject) }.let(::MessageDetails),
            type = MessageType.of(command.type),
            contentType = MessageContentType.of(command.contentType),
            countryCode = MessageCountryCode.of(command.countryCode),
            subject = command.subject,
            files = command.files.map { MessageFileId(it.fileId) }.let(::MessageFiles),
            messageReserveTime = command.messageReserveTime?.let { MessageReserveTime(command.messageReserveTime, command.messageReserveTimeUnit) },
        )
}
