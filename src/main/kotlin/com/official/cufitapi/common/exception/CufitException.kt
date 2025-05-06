package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

open class CufitException(private val errorCode: ErrorCode) : RuntimeException(errorCode.message) {
    fun getCode(): String = errorCode.name
}
