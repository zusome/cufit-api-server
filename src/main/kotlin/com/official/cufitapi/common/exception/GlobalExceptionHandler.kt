package com.official.cufitapi.common.exception

import com.official.cufitapi.domain.notification.infrastructure.DiscordWebhookClientAdapter
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordWebhookClientAdapter: DiscordWebhookClientAdapter
) {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        discordWebhookClientAdapter.sendErrorAlert(e)
        return ErrorResponse("500", e.message ?: "Internal Server Error")
    }
}


data class ErrorResponse(
    val code: String,
    val message: String
) {

}