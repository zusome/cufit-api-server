package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.IssueSmsAuthenticationCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationCodeGenerator
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.sms.event.RegisterSmsAuthenticationEvent
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationRepository
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

    override fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication {
        val smsAuthentication = smsAuthenticationRepository.save(init(command))
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
