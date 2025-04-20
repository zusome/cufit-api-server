package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.domain.auth.domain.member.vo.Authority

data class RefreshAuthorizationTokenCommand(
    val memberId: Long,
    val authority: Authority,
    val refreshToken: String
)
