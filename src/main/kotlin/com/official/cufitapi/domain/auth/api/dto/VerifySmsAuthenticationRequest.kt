package com.official.cufitapi.domain.auth.api.dto

data class VerifySmsAuthenticationRequest(
    val authCode: String,
    val phoneNumber: String,
)
