package com.official.cufitapi.domain.invitation.application.command

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode

data class InvitationCodeValidationCommand(
    val memberId: Long,
    val invitationCode: InvitationCode
) {
}