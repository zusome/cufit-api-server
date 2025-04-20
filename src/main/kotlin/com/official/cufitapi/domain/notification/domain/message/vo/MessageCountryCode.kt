package com.official.cufitapi.domain.notification.domain.message.vo

enum class MessageCountryCode(val value: String) {
    KOR("82");

    companion object {
        fun of(countryCode: String): MessageCountryCode = entries.firstOrNull { it.value == countryCode }
            ?: throw IllegalArgumentException("Invalid message country code: $countryCode")
    }
}
