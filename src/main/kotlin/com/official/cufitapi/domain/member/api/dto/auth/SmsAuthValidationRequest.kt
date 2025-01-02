package com.official.cufitapi.domain.member.api.dto.auth

import com.official.cufitapi.common.exception.InvalidRequestException

data class SmsAuthValidationRequest(
    val authCode: Int
) {
    init {
        if (authCode.toString().length != 6) {
            throw InvalidRequestException("잘못된 문자 인증 번호입니다. : $authCode")
        }

        if (authCode !in 100000..999999) {
            throw InvalidRequestException("잘못된 문자 인증 번호입니다. : $authCode")
        }

    }
}