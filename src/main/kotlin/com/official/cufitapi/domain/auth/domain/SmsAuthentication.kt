package com.official.cufitapi.domain.auth.domain

class SmsAuthentication (
    val phoneNumber: String,
    val authCode: String,
    val memberId: Long
) {
}