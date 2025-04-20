package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.member.infrastructure.ProfileImageUploadClientAdapter
import com.official.cufitapi.domain.notification.domain.message.Message
import com.official.cufitapi.domain.notification.domain.message.MessageProvider
import com.official.cufitapi.domain.notification.infrastructure.client.SensHttpClient
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessage
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessageFile
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessageRequest
import com.official.cufitapi.domain.notification.infrastructure.client.dto.SensMessageResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

// @Component
class DefaultMessageProvider(
    private val sensHttpClient: SensHttpClient,
) : MessageProvider {

    override fun send(
        message: Message,
    ): String {
        val messageRequest = mapToRequest(message)
        return sensHttpClient.sendMessage(messageRequest)
            .onFailure { logger.warn("알림 메시지 전송 불가") }
            .map(SensMessageResponse::requestId)
            .getOrNull()
            ?: throw IllegalStateException("알림 메시지 전송 실패")
    }

    private fun mapToRequest(message: Message): SensMessageRequest = SensMessageRequest(
        from = message.from,
        content = message.content,
        messages = message.messages.values.map { SensMessage(it.to, it.content, it.subject) },
        type = message.type.value,
        contentType = message.contentType.value,
        countryCode = message.countryCode.value,
        files = message.files?.values?.map { SensMessageFile(it.fileId) },
        subject = message.subject,
        reserveTime = message.messageReserveTime?.reserveTime,
        reserveTimeZone = message.messageReserveTime?.reserveTimeZone
    )

    companion object {
        private val logger = LoggerFactory.getLogger(DefaultMessageProvider::class.java)
    }
}
