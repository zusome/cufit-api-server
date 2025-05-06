package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.CufitException
import net.nurigo.sdk.NurigoApp.initialize
import com.official.cufitapi.domain.auth.application.command.IssueSmsAuthenticationCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationCodeGenerator
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.sms.event.RegisterSmsAuthenticationEvent
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationRepository
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService
import okio.IOException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface IssueSmsAuthenticationUseCase {
    fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication
}

interface VerifySmsAuthenticationUseCase {
    fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication
}

@Service
class SmsAuthenticationService(
    private val smsAuthenticationRepository: SmsAuthenticationRepository,
    private val smsAuthenticationCodeGenerator: SmsAuthenticationCodeGenerator,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : IssueSmsAuthenticationUseCase, VerifySmsAuthenticationUseCase {

    companion object {
        const val COOL_SMS_API_URL = "https://api.coolsms.co.kr"
        const val COOL_SMS_FROM_PHONE = "01087837803"
    }

    @Value("\${coolsms.api-key}")
    private lateinit var smsApiKey: String
    @Value("\${coolsms.secret-key}")
    private lateinit var smsSecretKey: String

    private val messageService: DefaultMessageService =
        initialize(smsApiKey, smsSecretKey, COOL_SMS_API_URL)

    override fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication {
        // message 전송
        val smsAuthentication = init(command)
        try {
            val message = Message(
                to = command.phone,
                from = COOL_SMS_FROM_PHONE,
                text = "인증번호는 ${smsAuthentication.code}입니다."
            )
            val response = messageService.sendOne(SingleMessageSendingRequest(message))
        } catch (e: IOException
        ) {
            throw CufitException(ErrorCode.INTERNAL_SERVER_ERROR)
        }

        smsAuthenticationRepository.save(init(command))
        applicationEventPublisher.publishEvent(registerSmsAuthenticationEvent(smsAuthentication))
        return smsAuthentication
    }

    private fun registerSmsAuthenticationEvent(smsAuthentication: SmsAuthentication): RegisterSmsAuthenticationEvent =
        RegisterSmsAuthenticationEvent(
            id = smsAuthentication.id!!,
            phone = smsAuthentication.phone,
            code = smsAuthentication.code,
            memberId = smsAuthentication.memberId,
            isVerified = smsAuthentication.isVerified
        )

    private fun init(command: IssueSmsAuthenticationCommand): SmsAuthentication =
        SmsAuthentication(
            phone = command.phone,
            code = smsAuthenticationCodeGenerator.generate(),
            memberId = command.memberId,
            false
        )

    override fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication {
        val smsAuthentication = smsAuthenticationRepository.findByMemberIdOrNull(command.memberId)
            ?: throw IllegalArgumentException("Invalid member id")
        smsAuthentication.verify(command.authCode)
        return smsAuthenticationRepository.save(smsAuthentication)
    }
}
