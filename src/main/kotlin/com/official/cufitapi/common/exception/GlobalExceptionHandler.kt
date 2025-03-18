package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.domain.notification.infrastructure.temp.DiscordWebhookClientAdapter
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordWebhookClientAdapter: DiscordWebhookClientAdapter
) {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ErrorResponse {
        return ErrorResponse(e.message.toString(), ErrorCode.valueOf(e.message.toString()).message)
    }

    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(e: UnAuthorizedException): ErrorResponse {
        return ErrorResponse(e.message.toString(), ErrorCode.valueOf(e.message.toString()).message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ErrorResponse {
        return ErrorResponse(e.message.toString(), ErrorCode.valueOf(e.message.toString()).message)
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(e: InternalServerErrorException): ErrorResponse {
        discordWebhookClientAdapter.sendErrorAlert(e)
        return ErrorResponse(e.message.toString(), ErrorCode.valueOf(e.message.toString()).message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        discordWebhookClientAdapter.sendErrorAlert(e)
        return ErrorResponse(e.message.toString(), ErrorCode.valueOf(e.message.toString()).message)
    }
}


data class ErrorResponse(
    val code: String,
    val message: String
) {

}
