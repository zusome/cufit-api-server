package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.domain.notification.infrastructure.temp.DiscordWebhookClientAdapter
import io.sentry.Sentry
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import kotlin.RuntimeException

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordWebhookClientAdapter: DiscordWebhookClientAdapter
) {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(e.getCode(), e.getCustomMessage())
    }

    @ExceptionHandler(UnAuthorizedException::class)
    fun handleUnAuthorizedException(e: UnAuthorizedException): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(e.getCode(), e.getCustomMessage())
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(e.getCode(), e.getCustomMessage())
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleInternalServerErrorException(e: InternalServerErrorException): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(e.getCode(), e.getCustomMessage())
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(ErrorCode.RUNTIME_EXCEPTION.name, ErrorCode.RUNTIME_EXCEPTION.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        logger.error(e.message, e)
        discordWebhookClientAdapter.sendErrorAlert(e)
        Sentry.captureException(e)
        return ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.name, ErrorCode.INTERNAL_SERVER_ERROR.message)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}


data class ErrorResponse(
    val code: String,
    val message: String
) {

}
