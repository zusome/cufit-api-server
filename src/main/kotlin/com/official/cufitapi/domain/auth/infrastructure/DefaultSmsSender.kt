package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.config.property.CoolsmsProperty
import com.official.cufitapi.common.exception.CufitException
import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.IOException

@Profile("prod")
@Component
class DefaultSmsSender(
    private val coolsmsProperty: CoolsmsProperty,
) : SmsSender {

    private val messageService: DefaultMessageService =
        initialize(coolsmsProperty.apiKey, coolsmsProperty.secretKey, coolsmsProperty.url)

    override fun send(to: String, text: String) {
        this.send(Message(from = coolsmsProperty.from, to = to, text = text))
    }

    private fun send(message: Message) {
        try {
            messageService.sendOne(SingleMessageSendingRequest(message))
        } catch (e: IOException) {
            throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}
