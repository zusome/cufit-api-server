package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

open class CufitException(errorCode: ErrorCode) : RuntimeException(errorCode.name)