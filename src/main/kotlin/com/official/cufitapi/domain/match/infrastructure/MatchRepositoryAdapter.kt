package com.official.cufitapi.domain.match.infrastructure

import com.official.cufitapi.domain.match.domain.Match
import com.official.cufitapi.domain.match.domain.MatchRepository
import com.official.cufitapi.domain.match.infrastructure.mapper.MatchMapper
import com.official.cufitapi.domain.match.infrastructure.persistence.JpaMatchRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class MatchRepositoryAdapter(
    private val matchMapper: MatchMapper,
    private val jpaMatchRepository: JpaMatchRepository
) : MatchRepository {

    @Transactional(readOnly = false)
    override fun save(match: Match): Match {
        val entity = jpaMatchRepository.save(matchMapper.mapToEntity(match))
        return matchMapper.mapToDomain(entity)
    }

    override fun findById(id: Long): Match {
        val entity = jpaMatchRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Match not found") }
        return matchMapper.mapToDomain(entity)
    }

    override fun findAllByPeriod(
        makerMemberId: Long,
        candidateId: Long,
        today: LocalDateTime,
        tomorrow: LocalDateTime,
    ): List<Match> = jpaMatchRepository.findAllByPeriod(makerMemberId, candidateId, today, tomorrow)
        .map { matchMapper.mapToDomain(it) }

    override fun findByCandidates(makerMemberId: Long, leftCandidateId: Long, rightCandidateId: Long): Match? {
        val entity = jpaMatchRepository.findByCandidates(makerMemberId, leftCandidateId, rightCandidateId)
        return entity?.let { matchMapper.mapToDomain(it) }
    }
}
