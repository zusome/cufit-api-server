package com.official.cufitapi.domain.auth.application.command

data class IssueSmsAuthenticationCommand(
    val memberId: Long,
    val phoneNumber: String
) {
}
