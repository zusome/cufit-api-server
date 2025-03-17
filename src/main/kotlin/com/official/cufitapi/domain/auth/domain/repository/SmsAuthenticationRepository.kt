package com.official.cufitapi.domain.auth.domain.repository

import com.official.cufitapi.domain.auth.domain.SmsAuthentication

interface SmsAuthenticationRepository {
    fun save(smsAuthentication: SmsAuthentication): SmsAuthentication
    fun findByMemberIdOrNull(memberId: Long): SmsAuthentication?
}
