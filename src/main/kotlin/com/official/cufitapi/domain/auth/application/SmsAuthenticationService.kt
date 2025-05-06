package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.IssueSmsAuthenticationCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationCodeGenerator
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.sms.event.RegisterSmsAuthenticationEvent
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationRepository
import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface IssueSmsAuthenticationUseCase {
    fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication
}

interface VerifySmsAuthenticationUseCase {
    fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication
}

// @Service
class SmsAuthenticationService(
    private val smsAuthenticationRepository: SmsAuthenticationRepository,
    private val smsAuthenticationCodeGenerator: SmsAuthenticationCodeGenerator,
    private val smsSender: SmsSender,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : IssueSmsAuthenticationUseCase, VerifySmsAuthenticationUseCase {

    companion object {
        const val COOL_SMS_API_URL = "https://api.coolsms.co.kr"
        const val COOL_SMS_FROM_PHONE = "01087837803"
    }

    override fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication {
        // message 전송
        val smsAuthentication = smsAuthenticationRepository.save(init(command))
        applicationEventPublisher.publishEvent(registerSmsAuthenticationEvent(smsAuthentication))
        smsSender.send(COOL_SMS_FROM_PHONE, command.phone, "인증번호는 ${smsAuthentication.code}입니다.")
        return smsAuthentication
    }

    private fun init(command: IssueSmsAuthenticationCommand): SmsAuthentication =
        SmsAuthentication(
            phone = command.phone,
            code = smsAuthenticationCodeGenerator.generate(),
            memberId = command.memberId,
            false
        )

    private fun registerSmsAuthenticationEvent(smsAuthentication: SmsAuthentication): RegisterSmsAuthenticationEvent =
        RegisterSmsAuthenticationEvent(
            id = smsAuthentication.id!!,
            phone = smsAuthentication.phone,
            code = smsAuthentication.code,
            memberId = smsAuthentication.memberId,
            isVerified = smsAuthentication.isVerified
        )

    override fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication {
        val smsAuthentication = smsAuthenticationRepository.findByMemberIdOrNull(command.memberId)
            ?: throw IllegalArgumentException("Invalid member id")
        smsAuthentication.verify(command.authCode)
        return smsAuthenticationRepository.save(smsAuthentication)
    }
}
