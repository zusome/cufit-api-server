package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MemberRelation
import com.official.cufitapi.domain.member.domain.repository.MemberRelationRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRelationRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMemberRelationMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMemberRelationRepository(
    private val jpaMemberRelationMapper: JpaMemberRelationMapper,
    private val jpaMemberRelationRepository: JpaMemberRelationRepository,
) : MemberRelationRepository {

    @Transactional(readOnly = false)
    override fun save(memberRelation: MemberRelation): MemberRelation {
        val entity = jpaMemberRelationMapper.mapToEntity(memberRelation)
        return jpaMemberRelationMapper.mapToDomain(jpaMemberRelationRepository.save(entity))
    }
}
