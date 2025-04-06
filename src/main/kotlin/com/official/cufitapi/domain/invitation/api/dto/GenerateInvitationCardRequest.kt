package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.invitation.application.command.GenerateInvitationCardCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대 카드 생성 요청")
data class GenerateInvitationCardRequest(
    @Schema(description = "후보자, 주선자", example = "MAKER/CANDIDATE")
    val memberType: String,
    @Schema(description = "후보자, 주선자의 관계", example = "FRIEND")
    val relationType: String,
) {
    fun toCommand(userId: Long): GenerateInvitationCardCommand =
        GenerateInvitationCardCommand(userId, memberType, relationType)
}
