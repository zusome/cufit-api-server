package com.official.cufitapi.domain.auth.domain.sms

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidSmsAuthenticationException

class SmsAuthentication(
    val phone: String,
    val code: String,
    val memberId: Long,
    var isVerified: Boolean,
    val id: Long? = null,
) {

    fun verify(authCode: String, phone: String) {
        if(this.code != authCode) {
            throw InvalidSmsAuthenticationException(ErrorCode.INVALID_SMS_AUTH_CODE_VERIFY)
        }
        if(this.phone != phone) {
            throw InvalidSmsAuthenticationException(ErrorCode.INVALID_SMS_PHONE_VERIFY)
        }
        isVerified = true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SmsAuthentication

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
