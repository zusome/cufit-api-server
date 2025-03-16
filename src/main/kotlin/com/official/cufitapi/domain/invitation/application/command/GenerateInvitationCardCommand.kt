package com.official.cufitapi.domain.invitation.application.command

data class GenerateInvitationCardCommand(
    val inviterId: Long,
    val invitationType: String,
    val invitationRelationType: String,
) {

}
