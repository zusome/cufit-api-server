package com.official.cufitapi.domain.api.dto.invitation

import com.official.cufitapi.domain.enums.MemberType

data class InvitationCodeGenerateRequest(
    val memberType: MemberType
)
