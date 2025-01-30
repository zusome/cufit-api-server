package com.official.cufitapi.domain.notification.api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "디바이스 토큰 등록 요청")
data class DeviceTokenRegisterRequest(
    @Schema(description = "플랫폼", example = "ANDROID")
    val platform: String,
    @Schema(description = "디바이스 토큰", example = "deviceToken")
    val deviceToken: String
)
