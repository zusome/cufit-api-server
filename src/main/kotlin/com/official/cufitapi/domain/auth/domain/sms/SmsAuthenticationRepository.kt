package com.official.cufitapi.domain.auth.domain.sms

interface SmsAuthenticationRepository {
    fun save(smsAuthentication: SmsAuthentication): SmsAuthentication
    fun findAll(memberId: Long): List<SmsAuthentication>
}
