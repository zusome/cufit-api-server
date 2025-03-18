package com.official.cufitapi.domain.invitation.domain.event

data class AcceptedInvitationCardEvent(
    var code: String,
    var inviterId: Long,
    var relationType: String,
    var isAccepted: Boolean,
    var inviteeId: Long,
    var id: Long,
) {

    companion object {
        fun mock(code: String, inviteeId: Long, relationType: String): AcceptedInvitationCardEvent {
            return AcceptedInvitationCardEvent(
                code = code,
                inviterId = 1,
                relationType = relationType,
                isAccepted = true,
                inviteeId = inviteeId,
                id = 1,
            )
        }
    }
}
