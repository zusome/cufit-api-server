package com.official.cufitapi.domain.member.application

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DiscordAlarmService {

    private val webClient = WebClient.create()

    // Discord 웹훅 Url은 환경 변수로 관리


    // Discord Webhook 전송 로직
    fun sendAlarm() {

    }
}