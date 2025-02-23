package com.official.cufitapi.common.api.dto

import org.springframework.http.HttpStatus

data class HttpResponse<T>(
    val code: String,
    val message: String,
    val payload: T?
) {
    companion object {
        fun <T> of(httpStatus: HttpStatus, payload: T?, message: String? = null): HttpResponse<T> = HttpResponse(
            httpStatus.value().toString(),
            message ?: httpStatus.reasonPhrase,
            payload
        )
    }
}