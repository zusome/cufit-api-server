package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

class InvalidSmsAuthenticationException(errorCode: ErrorCode): BadRequestException(errorCode) {
}
