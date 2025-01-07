package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.api.dto.auth.SmsAuthValidationRequest
import org.springframework.stereotype.Service

@Service
class SmsAuthenticationService {

    fun issueSmsAuthCode() {
        val authCode = generateRandomSmsAuthCode()
        // TODO: cache에 저장
        // TODO : 저장 성공하면 SMS 발송
        // TODO : 응답
    }

    fun validateSmsAuthCode(memberId: Long, request: SmsAuthValidationRequest) {
        // TODO: cache에서 authCode 꺼내오기
        // TODO : validation
    }

    private fun generateRandomSmsAuthCode(): String {
        return (100000..999999).random().toString()
    }

}