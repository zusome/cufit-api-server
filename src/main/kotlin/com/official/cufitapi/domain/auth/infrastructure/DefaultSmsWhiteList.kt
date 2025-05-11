package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.sms.SmsWhiteList
import com.official.cufitapi.domain.auth.infrastructure.persist.JpaSmsWhiteListRepository
import org.springframework.stereotype.Component

@Component
class DefaultSmsWhiteList(
    private val jpaSmsWhiteListRepository: JpaSmsWhiteListRepository,
) : SmsWhiteList {

    override fun isNotWhiteListed(phone: String): Boolean {
        val smsWhiteLists = jpaSmsWhiteListRepository.findAll()
        return smsWhiteLists.map { it.phone.replace("-", "") }.none { it == phone.replace("-", "") }
    }
}
