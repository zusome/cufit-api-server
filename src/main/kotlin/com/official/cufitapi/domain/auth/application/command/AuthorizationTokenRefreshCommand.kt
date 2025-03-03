package com.official.cufitapi.domain.auth.application.command

data class AuthorizationTokenRefreshCommand(
    val memberId: Long,
    val authority: String,
    val accessToken: String,
    val refreshToken: String
)
