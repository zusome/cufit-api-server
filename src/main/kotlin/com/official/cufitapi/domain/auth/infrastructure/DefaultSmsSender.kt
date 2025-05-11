package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.config.property.CoolsmsProperty
import com.official.cufitapi.common.exception.CufitException
import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class DefaultSmsSender(
    environment: Environment,
    private val coolsmsProperty: CoolsmsProperty,
) : SmsSender {

    private val activeProfiles = environment.activeProfiles.toList()
        .any { it == "prod" }

    private val messageService: DefaultMessageService =
        initialize(coolsmsProperty.apiKey, coolsmsProperty.secretKey, coolsmsProperty.url)

    override fun send(to: String, text: String) {
        if (activeProfiles) {
            logger.info("SMS 메시지 발송 모킹: $to, $text")
            return
        }
        this.send(Message(from = coolsmsProperty.from, to = to, text = text))
    }

    private fun send(message: Message) {
        try {
            messageService.sendOne(SingleMessageSendingRequest(message))
        } catch (e: IOException) {
            throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DefaultSmsSender::class.java)
    }
}
