package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCard
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대코드 응답")
data class InvitationCodeResponse(
    @Schema(description = "초대코드", example = "CA123456KA")
    val invitationCode: String
) {
    constructor(invitationCard: InvitationCard) : this(
      invitationCode = invitationCard.code
    )
}
