package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.invitation.application.command.AcceptInvitationCardCommand
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대 카드 수락")
data class AcceptInvitationCardRequest(
    @Schema(description = "초대 코드", example = "CA1234567A")
    val invitationCode: String,
) {
    fun toCommand(userId: Long): AcceptInvitationCardCommand =
        AcceptInvitationCardCommand(userId, invitationCode)
}
