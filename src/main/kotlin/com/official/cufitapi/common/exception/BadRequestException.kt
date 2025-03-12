package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

open class BadRequestException(errorCode: ErrorCode) : CufitException(errorCode)