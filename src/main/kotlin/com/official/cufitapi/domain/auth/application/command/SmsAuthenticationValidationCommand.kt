package com.official.cufitapi.domain.auth.application.command

data class SmsAuthenticationValidationCommand(
    val memberId: Long,
    val authCode: String
)