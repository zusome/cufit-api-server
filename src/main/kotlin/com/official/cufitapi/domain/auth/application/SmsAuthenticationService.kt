package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidSmsAuthenticationException
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.auth.application.command.IssueSmsAuthenticationCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationCodeGenerator
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationRepository
import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import com.official.cufitapi.domain.auth.domain.sms.SmsWhiteList
import com.official.cufitapi.domain.auth.domain.sms.event.RegisterSmsAuthenticationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

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
    private val smsWhiteList: SmsWhiteList,
    private val smsSender: SmsSender,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : IssueSmsAuthenticationUseCase, VerifySmsAuthenticationUseCase {

    override fun issue(command: IssueSmsAuthenticationCommand): SmsAuthentication {
        if(smsWhiteList.isNotWhiteListed(command.phone)) {
            throw InvalidSmsAuthenticationException(ErrorCode.INVALID_SMS_PHONE)
        }
        val smsAuthentication = smsAuthenticationRepository.save(init(command))
        applicationEventPublisher.publishEvent(registerSmsAuthenticationEvent(smsAuthentication))
        CompletableFuture.runAsync { smsSender.send(command.phone, "[큐핏] 인증번호는 ${smsAuthentication.code}입니다. (타인 노출 주의)") }
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
            ?: throw NotFoundException(ErrorCode.INTERNAL_SERVER_ERROR)
        smsAuthentication.verify(command.authCode, command.phone)
        return smsAuthenticationRepository.save(smsAuthentication)
    }
}
