package com.official.cufitapi.auth.application.command

data class MemberRegistrationCommand(
    val name: String,
    val email: String,
    val provider: String,
    val providerId: String,
)

