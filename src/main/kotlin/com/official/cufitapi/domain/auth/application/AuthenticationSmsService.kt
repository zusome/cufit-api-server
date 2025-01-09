package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationIssueCommand
import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationValidationCommand
import org.springframework.stereotype.Service

interface AuthenticationSmsIssueUseCase {
    fun issueSmsAuthCode(command: SmsAuthenticationIssueCommand)
}

interface AuthenticationSmsValidateUseCase {
    fun validateSmsAuthCode(command: SmsAuthenticationValidationCommand)
}

@Service
class AuthenticationSmsService(
) : AuthenticationSmsIssueUseCase, AuthenticationSmsValidateUseCase {

    override fun issueSmsAuthCode(command: SmsAuthenticationIssueCommand) {
        val authCode = generateRandomSmsAuthCode()
        // TODO: cache에 저장
        // TODO : 저장 성공하면 SMS 발송
        // TODO : 응답
    }

    override fun validateSmsAuthCode(command: SmsAuthenticationValidationCommand) {
        // TODO: cache에서 authCode 꺼내오기
        // TODO : validation
    }

    private fun generateRandomSmsAuthCode(): String {
        return (100000..999999).random().toString()
    }
}
