package com.official.cufitapi.domain.api.dto.invitation

import com.official.cufitapi.domain.domain.vo.InvitationCode

data class InvitationCodeResponse(
    val invitationCode: String
) {
    constructor(invitationCode: InvitationCode) : this(
      invitationCode = invitationCode.code
    )
}
