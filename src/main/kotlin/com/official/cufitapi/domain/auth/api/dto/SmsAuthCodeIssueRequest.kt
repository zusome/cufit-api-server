package com.official.cufitapi.domain.auth.api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "문자 인증 코드 발급 요청")
data class SmsAuthCodeIssueRequest(
    @Schema(name = "휴대폰 번호", example = "010-1234-1234")
    val phoneNumber: String
)
