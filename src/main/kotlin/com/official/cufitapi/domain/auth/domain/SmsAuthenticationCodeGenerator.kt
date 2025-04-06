package com.official.cufitapi.domain.auth.domain

fun interface SmsAuthenticationCodeGenerator {
    fun generate(): String
}
