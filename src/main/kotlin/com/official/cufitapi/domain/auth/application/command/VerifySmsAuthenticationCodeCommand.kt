package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException

data class VerifySmsAuthenticationCodeCommand(
    val memberId: Long,
    val phone: String,
    val authCode: String,
) {
    init {
        if (authCode.length != 6) {
            throw InvalidRequestException(ErrorCode.INVALID_SMS_AUTH_CODE)
        }

        if (!authCode.matches(Regex("\\d{6}"))) {
            throw InvalidRequestException(ErrorCode.INVALID_SMS_AUTH_CODE)
        }
    }
}
