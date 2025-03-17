package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MatchCandidate
import com.official.cufitapi.domain.member.domain.repository.MatchCandidateRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidateRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMatchCandidateMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMatchCandidateRepository(
    private val jpaMatchCandidateMapper: JpaMatchCandidateMapper,
    private val jpaMatchCandidateRepository: JpaMatchCandidateRepository,
) : MatchCandidateRepository {

    @Transactional(readOnly = false)
    override fun save(matchCandidate: MatchCandidate): MatchCandidate {
        val entity = jpaMatchCandidateMapper.mapToEntity(matchCandidate)
        return jpaMatchCandidateMapper.mapToDomain(jpaMatchCandidateRepository.save(entity))
    }

    @Transactional(readOnly = true)
    override fun findByMemberId(memberId: Long): MatchCandidate {
        return jpaMatchCandidateRepository.findByMemberId(memberId)
            ?.let(jpaMatchCandidateMapper::mapToDomain)
            ?: throw RuntimeException("후보자가 아닙니다.")
    }
}
