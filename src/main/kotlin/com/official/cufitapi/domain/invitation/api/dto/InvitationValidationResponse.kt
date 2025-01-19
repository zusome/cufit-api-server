package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.member.domain.vo.MemberType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대 검증 응답")
data class InvitationValidationResponse(
    @Schema(description = "사용자 타입", example = "CANDIDATE")
    val memberType: MemberType
) {
}