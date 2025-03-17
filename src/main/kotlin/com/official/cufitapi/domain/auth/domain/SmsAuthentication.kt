package com.official.cufitapi.domain.auth.domain

class SmsAuthentication(
    val phoneNumber: String,
    val authCode: String,
    val memberId: Long,
    var isVerified: Boolean,
    val id: Long? = null,
) {

    fun verify(authCode: String) {
        if(this.authCode != authCode) {
            throw IllegalArgumentException("인증번호가 일치하지 않습니다.")
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
