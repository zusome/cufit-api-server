package com.official.cufitapi.domain.invitation.api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대 카드 수락 응답")
data class AcceptInvitationCardResponse(
    @Schema(description = "사용자 타입", example = "CANDIDATE")
    val memberType: String,

    @Schema(description = "초대자 이름", example = "관리자")
    val inviterName: String,
)
