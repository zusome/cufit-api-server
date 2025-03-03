package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.domain.auth.domain.vo.Authority

data class AuthorizationTokenRefreshCommand(
    val memberId: Long,
    val authority: Authority,
    val refreshToken: String
)
