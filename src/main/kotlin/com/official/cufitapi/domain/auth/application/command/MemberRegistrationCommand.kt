package com.official.cufitapi.domain.auth.application.command

data class MemberRegistrationCommand(
    val username: String? = null,
    val email: String? = null,
    val provider: String,
    val providerId: String,
)
