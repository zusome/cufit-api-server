package com.official.cufitapi.domain.auth.api.dto

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException

data class SmsAuthValidationRequest(
    val authCode: String
) {
    init {
        if (authCode.toString().length != 6) {
            throw InvalidRequestException(ErrorCode.INVALID_SMS_AUTH_CODE)
        }

        if (!authCode.matches(Regex("\\d{6}"))) {
            throw InvalidRequestException(ErrorCode.INVALID_SMS_AUTH_CODE)
        }
    }
}