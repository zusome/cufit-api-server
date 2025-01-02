package com.official.cufitapi.domain.auth.domain

import com.official.cufitapi.domain.auth.domain.vo.AccessToken
import com.official.cufitapi.domain.auth.domain.vo.RefreshToken

class AuthorizationToken(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthorizationToken

        if (accessToken != other.accessToken) return false
        if (refreshToken != other.refreshToken) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accessToken.hashCode()
        result = 31 * result + refreshToken.hashCode()
        return result
    }
}
