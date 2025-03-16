package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

class ValidationFailException(errorCode: ErrorCode) : BadRequestException(errorCode)