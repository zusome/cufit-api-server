package com.official.cufitapi.domain.member.domain

class MemberRelation(
    var inviterId: Long,
    var inviteeId: Long,
    var relationType: String,
    var id: Long? = null,
) {
}
