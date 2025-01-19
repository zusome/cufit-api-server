package com.official.cufitapi.domain.auth.application.command

data class SmsAuthenticationIssueCommand(
    val memberId: Long,
    val phoneNumber: String
) {
}