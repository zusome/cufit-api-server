package com.official.cufitapi.domain.api.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(name = "사용자 정보 응답")
data class MemberInfoResponse(
    val name: String,
    val email: String
)
