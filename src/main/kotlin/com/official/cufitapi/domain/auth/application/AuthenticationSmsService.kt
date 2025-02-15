package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationIssueCommand
import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationValidationCommand
import com.official.cufitapi.domain.auth.domain.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationMemberRepository
import com.official.cufitapi.domain.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service

interface AuthenticationSmsIssueUseCase {
    fun issueSmsAuthCode(command: SmsAuthenticationIssueCommand) : SmsAuthentication
}

interface AuthenticationSmsValidateUseCase {
    fun validateSmsAuthCode(command: SmsAuthenticationValidationCommand)
}

@Service
class AuthenticationSmsService(
    private val authorizationMemberRepository: AuthorizationMemberRepository
) : AuthenticationSmsIssueUseCase, AuthenticationSmsValidateUseCase {

    override fun issueSmsAuthCode(command: SmsAuthenticationIssueCommand): SmsAuthentication {
        val authCode = generateRandomSmsAuthCode()
        val smsAuthentication = SmsAuthentication(
            phoneNumber = command.phoneNumber,
            // FIXME : generateRandomSmsAuthCode()를 사용하여 authCode를 생성.
            authCode = "123456",
            memberId = command.memberId
        )
        authorizationMemberRepository.saveAuthCode(smsAuthentication)
        return smsAuthentication
    }

    override fun validateSmsAuthCode(command: SmsAuthenticationValidationCommand) {
        val authorizationMember = authorizationMemberRepository.findById(command.memberId)
        authorizationMember.smsAuthCode?.let {
            if (it != command.authCode) {
                throw IllegalArgumentException("인증번호가 일치하지 않습니다.")
            }
        }
    }

    private fun generateRandomSmsAuthCode(): String {
        return (100000..999999).random().toString()
    }
}
