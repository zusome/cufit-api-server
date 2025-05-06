package com.official.cufitapi.domain.auth.domain.sms

interface SmsSender {
    fun send(from: String, to: String, text: String)
}
