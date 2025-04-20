package com.official.cufitapi.domain.match.domain

import com.official.cufitapi.domain.member.domain.MemberRelation

interface MemberRelations {
    fun findByInviterIdAndInviteeId(inviterId: Long, inviteeId: Long): MemberRelation?
}
