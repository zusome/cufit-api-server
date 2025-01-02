package com.official.cufitapi.domain.member.application.command.invitation

data class InvitationCodeValidationCommand(
    val memberId: Long,
    val invitationCode: com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode
) {
}