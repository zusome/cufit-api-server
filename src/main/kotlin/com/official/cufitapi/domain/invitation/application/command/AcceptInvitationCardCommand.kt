package com.official.cufitapi.domain.invitation.application.command

data class AcceptInvitationCardCommand(
    val inviteeId: Long,
    val invitationCode: String,
) {
}
