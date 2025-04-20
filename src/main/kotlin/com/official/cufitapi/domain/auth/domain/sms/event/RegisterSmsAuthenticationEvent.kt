package com.official.cufitapi.domain.auth.domain.sms.event

data class RegisterSmsAuthenticationEvent(
    val id: Long,
    val phone: String,
    val code: String,
    val memberId: Long,
    var isVerified: Boolean,
)
