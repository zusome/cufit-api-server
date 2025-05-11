package com.official.cufitapi.domain.auth.domain.sms

fun interface SmsSender {
    fun send(to: String, text: String)
}
