package com.official.cufitapi.domain.invitation.application.command

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCard

data class InvitationCardAcceptCommand(
    val inviteeId: Long,
    val invitationCode: InvitationCard
) {
}