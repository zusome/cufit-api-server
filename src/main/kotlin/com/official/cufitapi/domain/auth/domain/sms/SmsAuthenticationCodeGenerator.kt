package com.official.cufitapi.domain.auth.domain.sms

fun interface SmsAuthenticationCodeGenerator {
    fun generate(): String
}
