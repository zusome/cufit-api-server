package com.official.cufitapi.domain.auth.domain.repository

import com.official.cufitapi.domain.auth.domain.AuthorizationToken
import com.official.cufitapi.domain.auth.domain.vo.AccessToken

interface AuthorizationTokenRepository {
    fun save(authorizationToken: AuthorizationToken): AuthorizationToken
    fun findByMemberIdAndAccessToken(memberId: Long, accessToken: AccessToken): AuthorizationToken?
}
