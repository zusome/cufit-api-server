package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class MemberRelationDto(
    var inviterId: Long,
    var inviteeId: Long,
    var relationType: String,
    var id: Long,
)
