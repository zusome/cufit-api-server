package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.Candidate
import com.official.cufitapi.domain.member.domain.repository.CandidateRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaCandidateRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaCandidateMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultCandidateRepository(
    private val jpaCandidateMapper: JpaCandidateMapper,
    private val jpaCandidateRepository: JpaCandidateRepository,
) : CandidateRepository {

    @Transactional(readOnly = false)
    override fun save(candidate: Candidate): Candidate {
        val entity = jpaCandidateMapper.mapToEntity(candidate)
        return jpaCandidateMapper.mapToDomain(jpaCandidateRepository.save(entity))
    }

    @Transactional(readOnly = true)
    override fun findByMemberId(memberId: Long): Candidate {
        return jpaCandidateRepository.findByMemberId(memberId)
            ?.let(jpaCandidateMapper::mapToDomain)
            ?: throw RuntimeException("후보자가 아닙니다.")
    }
}
