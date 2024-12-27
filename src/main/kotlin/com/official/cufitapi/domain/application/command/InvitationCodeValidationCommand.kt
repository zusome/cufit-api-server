package com.official.cufitapi.domain.application.command

import com.official.cufitapi.domain.domain.vo.InvitationCode

data class InvitationCodeValidationCommand(
    val memberId: Long,
    val invitationCode: InvitationCode
) {
}