package com.official.cufitapi.domain.auth.application.command

data class MemberRegistrationCommand(
    val username: String,
    val email: String,
    val provider: String,
    val providerId: String,
)
