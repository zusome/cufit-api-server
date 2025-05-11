package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.sms.SmsSender
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!prod")
@Component
class LocalSmsSender(
) : SmsSender {

    override fun send(to: String, text: String) {
        logger.info("Sending SMS to $to: $text")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LocalSmsSender::class.java)
    }
}
