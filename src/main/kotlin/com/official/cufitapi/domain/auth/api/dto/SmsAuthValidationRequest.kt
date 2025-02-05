package com.official.cufitapi.domain.auth.api.dto

import com.official.cufitapi.common.exception.InvalidRequestException

data class SmsAuthValidationRequest(
    val authCode: String
) {
    init {
        if (authCode.toString().length != 6) {
            throw InvalidRequestException("잘못된 문자 인증 번호입니다. : $authCode")
        }

        if (!authCode.matches(Regex("\\d{6}"))) {
            throw InvalidRequestException("잘못된 문자 인증 번호입니다. : $authCode")
        }
    }
}