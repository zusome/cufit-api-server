package com.official.cufitapi.domain.invitation.domain.event

data class RegisteredInvitationCardEvent(
    var code: String,
    var inviterId: Long,
    var relationType: String,
    var isAccepted: Boolean,
    var inviteeId: Long?,
    var id: Long,
)
