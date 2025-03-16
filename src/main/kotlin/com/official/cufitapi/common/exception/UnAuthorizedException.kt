package com.official.cufitapi.common.exception

import com.official.cufitapi.common.config.ErrorCode

class UnAuthorizedException(errorCode: ErrorCode) : CufitException(errorCode)