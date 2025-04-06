package com.official.cufitapi.domain.arrangement.infrastructure

import com.official.cufitapi.domain.arrangement.domain.Arrangement
import com.official.cufitapi.domain.arrangement.domain.ArrangementRepository
import com.official.cufitapi.domain.arrangement.infrastructure.mapper.ArrangementMapper
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class ArrangementRepositoryAdapter(
    private val arrangementMapper: ArrangementMapper,
    private val arrangementJpaRepository: ArrangementJpaRepository
) : ArrangementRepository {

    @Transactional(readOnly = false)
    override fun save(arrangement: Arrangement): Arrangement {
        val entity = arrangementJpaRepository.save(arrangementMapper.mapToEntity(arrangement))
        return arrangementMapper.mapToDomain(entity)
    }

    override fun findById(id: Long): Arrangement {
        val entity = arrangementJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Arrangement not found") }
        return arrangementMapper.mapToDomain(entity)
    }

    override fun findAllByPeriod(
        matchMakerId: Long,
        candidateId: Long,
        today: LocalDateTime,
        tomorrow: LocalDateTime,
    ): List<Arrangement> = arrangementJpaRepository.findAllByPeriod(matchMakerId, candidateId, today, tomorrow)
        .map { arrangementMapper.mapToDomain(it) }

    override fun findByCandidates(matchMakerId: Long, leftCandidateId: Long, rightCandidateId: Long): Arrangement? {
        val entity = arrangementJpaRepository.findByCandidates(matchMakerId, leftCandidateId, rightCandidateId)
        return entity?.let { arrangementMapper.mapToDomain(it) }
    }
}
