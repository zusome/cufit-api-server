package com.official.cufitapi.domain.invitation.domain

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType

class InvitationCard(
    var code: InvitationCode,
    var inviterId: Long,
    var relationType: InvitationRelationType,
    var isAccepted: Boolean,
    var inviteeId: Long? = null,
    var id: Long? = null,
) {

    fun accept(inviteeId: Long) {
        if(isAccepted) {
            throw IllegalStateException("이미 수락된 초대장입니다.")
        }
        this.inviteeId = inviteeId
        this.isAccepted = true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InvitationCard

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
