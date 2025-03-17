package com.official.cufitapi.domain.auth.application.service

import org.springframework.stereotype.Component

fun interface SmsAuthenticationCodeGenerator {
    fun generate(): String
}

@Component
class SmsAuthenticationCodeGeneratorImpl : SmsAuthenticationCodeGenerator {
    override fun generate(): String {
        return (100000..999999).random().toString()
    }
}
