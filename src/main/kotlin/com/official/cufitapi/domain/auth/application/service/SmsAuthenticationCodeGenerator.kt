package com.official.cufitapi.domain.auth.application.service

import com.official.cufitapi.domain.auth.domain.SmsAuthenticationCodeGenerator
import org.springframework.stereotype.Component

@Component
class SmsAuthenticationCodeGeneratorImpl : SmsAuthenticationCodeGenerator {
    override fun generate(): String {
        return (100000..999999).random().toString()
    }
}
