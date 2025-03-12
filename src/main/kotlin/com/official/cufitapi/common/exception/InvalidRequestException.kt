package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

class InvalidRequestException(errorCode: ErrorCode) : BadRequestException(errorCode)