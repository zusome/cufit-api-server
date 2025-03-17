package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.MemberRelation
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRelation
import org.springframework.stereotype.Component

@Component
class JpaMemberRelationMapper {

    fun mapToDomain(entity: JpaMemberRelation): MemberRelation {
        return MemberRelation(
            inviterId = entity.inviterId,
            inviteeId = entity.inviteeId,
            relationType = entity.relationType,
            id = entity.id!!
        )
    }

    fun mapToEntity(memberRelation: MemberRelation): JpaMemberRelation {
        return JpaMemberRelation(
            inviterId = memberRelation.inviterId,
            inviteeId = memberRelation.inviteeId,
            relationType = memberRelation.relationType
        )
    }
}
