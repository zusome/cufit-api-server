package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

class InternalServerErrorException(errorCode: ErrorCode) : CufitException(errorCode)