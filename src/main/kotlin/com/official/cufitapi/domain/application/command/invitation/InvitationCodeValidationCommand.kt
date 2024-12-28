package com.official.cufitapi.domain.application.command.invitation

import com.official.cufitapi.domain.domain.invitation.vo.InvitationCode

data class InvitationCodeValidationCommand(
    val memberId: Long,
    val invitationCode: InvitationCode
) {
}