package com.official.cufitapi.domain.auth.application.service

import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationCodeGenerator
import org.springframework.stereotype.Component

@Component
class RandomSmsAuthenticationCodeGenerator : SmsAuthenticationCodeGenerator {

    override fun generate(): String {
        return (100000..999999).random().toString()
    }
}
