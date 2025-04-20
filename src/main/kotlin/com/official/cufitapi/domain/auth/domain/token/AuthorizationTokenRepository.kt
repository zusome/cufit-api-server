package com.official.cufitapi.domain.auth.domain.token

interface AuthorizationTokenRepository {
    fun save(authorizationToken: AuthorizationToken): AuthorizationToken
    fun findByMemberId(memberId: Long): AuthorizationToken?
}
