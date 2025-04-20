package com.official.cufitapi.domain.notification.api.http.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "회원 알림 동의 응답")
data class AgreeMemberAlarmResponse(
    @Schema(description = "동의 여부", example = "true")
    val agree: Boolean,
    @Schema(description = "알림 타입", example = "PUSH")
    val alarmType: String
)
