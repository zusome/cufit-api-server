package com.official.cufitapi.domain.invitation.api.dto

import com.official.cufitapi.domain.invitation.domain.InvitationCard
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대 카드 생성 응답")
data class GenerateInvitationCardResponse(
    @Schema(description = "초대 코드", example = "CA123456KA")
    val invitationCode: String
) {
    constructor(invitationCode: InvitationCard) : this(
      invitationCode = invitationCode.code.value
    )
}
