package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MemberRelation
import com.official.cufitapi.domain.member.domain.repository.MemberRelationRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberRelationRepositoryAdapter(
    private val memberRelationJpaRepository: MemberRelationJpaRepository,
) : MemberRelationRepository {

    @Transactional(readOnly = false)
    override fun save(memberRelation: MemberRelation): MemberRelation {
        val entity = toEntity(memberRelation)
        return toDomain(memberRelationJpaRepository.save(entity))
    }

    private fun toDomain(entity: MemberRelationEntity): MemberRelation {
        return MemberRelation(
            inviterId = entity.inviterId,
            inviteeId = entity.inviteeId,
            relationType = entity.relationType,
            id = entity.id!!
        )
    }

    private fun toEntity(memberRelation: MemberRelation): MemberRelationEntity {
        return MemberRelationEntity(
            inviterId = memberRelation.inviterId,
            inviteeId = memberRelation.inviteeId,
            relationType = memberRelation.relationType
        )
    }
}
