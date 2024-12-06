package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MatchCandidate
import org.springframework.data.jpa.repository.JpaRepository

interface CandidateJpaRepository : JpaRepository<MatchCandidate, Long> {
    fun findByMatchMakerId(matchMakerId: Long) : List<MatchCandidate>
}