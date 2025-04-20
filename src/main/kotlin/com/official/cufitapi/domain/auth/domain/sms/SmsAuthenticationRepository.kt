package com.official.cufitapi.domain.auth.domain.sms

interface SmsAuthenticationRepository {
    fun save(smsAuthentication: SmsAuthentication): SmsAuthentication
    fun findByMemberIdOrNull(memberId: Long): SmsAuthentication?
}
