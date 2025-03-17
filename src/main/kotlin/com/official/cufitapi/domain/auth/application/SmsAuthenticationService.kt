package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationIssueCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import com.official.cufitapi.domain.auth.application.service.SmsAuthenticationCodeGenerator
import com.official.cufitapi.domain.auth.domain.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.repository.SmsAuthenticationRepository
import org.springframework.stereotype.Service

interface IssueSmsAuthenticationUseCase {
    fun issue(command: SmsAuthenticationIssueCommand): SmsAuthentication
}

interface VerifySmsAuthenticationUseCase {
    fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication
}

@Service
class SmsAuthenticationService(
    private val smsAuthenticationRepository: SmsAuthenticationRepository,
    private val smsAuthenticationCodeGenerator: SmsAuthenticationCodeGenerator,
) : IssueSmsAuthenticationUseCase, VerifySmsAuthenticationUseCase {

    override fun issue(command: SmsAuthenticationIssueCommand): SmsAuthentication {
        val smsAuthentication = SmsAuthentication(
            phoneNumber = command.phoneNumber,
            authCode = smsAuthenticationCodeGenerator.generate(),
            memberId = command.memberId,
            false
        )
        return smsAuthenticationRepository.save(smsAuthentication)
    }

    override fun verify(command: VerifySmsAuthenticationCodeCommand): SmsAuthentication {
        val smsAuthentication = smsAuthenticationRepository.findByMemberIdOrNull(command.memberId)
            ?: throw IllegalArgumentException("Invalid member id")
        smsAuthentication.verify(command.authCode)
        return smsAuthenticationRepository.save(smsAuthentication)
    }
}
