package com.official.cufitapi.domain.arrangement.infrastructure

import com.official.cufitapi.domain.arrangement.domain.MemberRelations
import com.official.cufitapi.domain.member.domain.MemberRelation
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRelationRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMemberRelationMapper
import org.springframework.stereotype.Component

@Component
class DefaultMemberRelations(
    private val memberRelationMapper: JpaMemberRelationMapper,
    private val memberRelations: JpaMemberRelationRepository,
) : MemberRelations {
    override fun findByInviterIdAndInviteeId(inviterId: Long, inviteeId: Long): MemberRelation? {
        return memberRelations.findByInviterIdAndInviteeId(inviterId, inviteeId)
            ?.let { memberRelationMapper.mapToDomain(it) }
    }
}
