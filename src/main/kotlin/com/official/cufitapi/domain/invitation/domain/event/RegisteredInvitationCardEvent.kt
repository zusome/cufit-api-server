package com.official.cufitapi.domain.invitation.domain.event

data class RegisteredInvitationCardEvent(
    var code: String,
    var inviterId: Long,
    var relationType: String,
    var isAccepted: Boolean,
    var inviteeId: Long,
    var id: Long,
) {

    companion object {
        fun mock(code: String, inviteeId: Long, relationType: String): RegisteredInvitationCardEvent {
            return RegisteredInvitationCardEvent(
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
