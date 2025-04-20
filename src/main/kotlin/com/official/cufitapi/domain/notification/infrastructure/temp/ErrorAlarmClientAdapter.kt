package com.official.cufitapi.domain.notification.infrastructure.temp

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.servlet.resource.NoResourceFoundException

@Component
class DiscordWebhookClientAdapter {
    private val restClient: RestClient = RestClient.create()

    fun sendErrorAlert(exception: Exception) {
        if (exception is NoResourceFoundException) {
            val requestBody =
                DiscordRequest(
                    content = "에러 발생 : ${exception.message} ( ${exception.javaClass.simpleName} )",
                    embeds = null,
                )
            restClient
                .post()
                .uri("https://discord.com/api/webhooks/1334213279626563644/8jr8iw_abqa4v4gFwHx4wJq0S81F9ci4WVwwEodNRTMLcGVmadrulMh54PIGwq7Ps4U4")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(requestBody)
                .retrieve()
            return
        }
        val requestBody =
            DiscordRequest(
                content = "에러 발생 : ${exception.message} ( ${exception.javaClass.simpleName} )",
                listOf(
                    Embed(
                        title = "Stack Trace",
                        description = parseStackTrace(exception),
                    ),
                ),
            )
        restClient
            .post()
            .uri("https://discord.com/api/webhooks/1334213279626563644/8jr8iw_abqa4v4gFwHx4wJq0S81F9ci4WVwwEodNRTMLcGVmadrulMh54PIGwq7Ps4U4")
            .body(requestBody)
            .retrieve()
    }

    private fun parseStackTrace(exception: Exception): String {
        val stackTrace = exception.stackTraceToString()
        if (stackTrace.length > 2000) {
            return stackTrace.substring(0, 2000)
        }
        return stackTrace
    }
}

data class DiscordRequest(
    val content: String,
    val embeds: List<Embed>?,
)

data class Embed(
    val title: String,
    val description: String,
)
