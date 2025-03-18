package com.official.cufitapi.domain.notification.infrastructure.temp

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.servlet.resource.NoResourceFoundException

@Component
class DiscordWebhookClientAdapter(
    @Value("\${discord.error-alert-url}")
    private val ERROR_ALERT_URL: String
) {
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
                .uri(ERROR_ALERT_URL)
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
            .uri(ERROR_ALERT_URL)
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
