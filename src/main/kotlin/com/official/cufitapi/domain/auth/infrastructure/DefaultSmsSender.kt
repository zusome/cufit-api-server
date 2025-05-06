package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.CufitException
import com.official.cufitapi.domain.auth.application.SmsAuthenticationService.Companion.COOL_SMS_API_URL
import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException

// @Component
class DefaultSmsSender(
    @Value("\${coolsms.api-key}")
    private val smsApiKey: String,
    @Value("\${coolsms.secret-key}")
    private val smsSecretKey: String,
) : SmsSender {

    private val messageService: DefaultMessageService =
        initialize(smsApiKey, smsSecretKey, COOL_SMS_API_URL)

    override fun send(from: String, to: String, text: String) {
        this.send(Message(from = from, to = to, text = text))
    }

    private fun send(message: Message) {
        try {
            messageService.sendOne(SingleMessageSendingRequest(message))
        } catch (e: IOException) {
            throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
        }
    }
}
