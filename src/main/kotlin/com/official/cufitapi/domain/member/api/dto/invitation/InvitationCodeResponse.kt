package com.official.cufitapi.domain.member.api.dto.invitation

import com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대코드 응답")
data class InvitationCodeResponse(
    @Schema(description = "초대코드", example = "CA123456KA")
    val invitationCode: String
) {
    constructor(invitationCode: com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode) : this(
      invitationCode = invitationCode.code
    )
}
