package com.official.cufitapi.common.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse("500", e.message ?: "Internal Server Error")
    }

}


data class ErrorResponse(
    val code: String,
    val message: String
) {

}