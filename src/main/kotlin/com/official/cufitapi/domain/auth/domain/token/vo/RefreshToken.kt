package com.official.cufitapi.domain.auth.domain.token.vo

data class RefreshToken(
    val refreshToken: String
) {
    fun isNotSame(refreshToken: String): Boolean {
        return this.refreshToken != refreshToken
    }
}
