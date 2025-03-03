package com.official.cufitapi.domain.auth.domain.vo

data class RefreshToken(
    val refreshToken: String
) {
    fun isNotSame(refreshToken: String): Boolean {
        return this.refreshToken != refreshToken
    }
}
