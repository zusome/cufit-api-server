package com.official.cufitapi.domain.auth.application.command

data class VerifySmsAuthenticationCodeCommand(
    val memberId: Long,
    val phoneNumber: String,
    val authCode: String
)
