package com.official.cufitapi.domain.auth.domain.sms

fun interface SmsWhiteList {
    fun isNotWhiteListed(phone: String): Boolean
}
