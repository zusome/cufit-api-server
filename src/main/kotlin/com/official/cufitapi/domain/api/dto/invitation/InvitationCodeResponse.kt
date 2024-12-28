package com.official.cufitapi.domain.api.dto.invitation

import com.official.cufitapi.domain.domain.vo.InvitationCode
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "초대코드 응답")
data class InvitationCodeResponse(
    @Schema(description = "초대코드", example = "CA123456KA")
    val invitationCode: String
) {
    constructor(invitationCode: InvitationCode) : this(
      invitationCode = invitationCode.code
    )
}
