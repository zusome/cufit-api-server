package com.official.cufitapi.common.api

import org.springframework.http.HttpStatus

data class HttpResponse<T>(
    val code: String,
    val message: String,
    val payload: T
) {
    companion object {
        fun <T> of(httpStatus: HttpStatus, payload: T, message: String? = null): HttpResponse<T> = HttpResponse(
            httpStatus.toString(),
            message ?: httpStatus.reasonPhrase,
            payload
        )
    }
}