package com.official.cufitapi.domain.auth.domain.repository

import com.official.cufitapi.domain.auth.domain.AuthorizationToken

interface AuthorizationTokenRepository {
    fun save(authorizationToken: AuthorizationToken): AuthorizationToken
    fun findByMemberId(memberId: Long): AuthorizationToken?
}
