package com.official.cufitapi.domain.auth.domain.repository

import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.SmsAuthentication

interface AuthorizationMemberRepository {
    fun register(authorizationMember: AuthorizationMember): AuthorizationMember
    fun findById(memberId: Long): AuthorizationMember
    fun saveAuthCode(smsAuthentication: SmsAuthentication)
}