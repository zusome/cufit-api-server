package com.official.cufitapi.auth.application.command

data class MemberRegistrationCommand(
    val username: String,
    val email: String,
    val provider: String,
    val providerId: String,
)
