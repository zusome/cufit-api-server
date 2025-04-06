package com.official.cufitapi.domain.match.domain

import java.time.LocalDateTime

interface MatchRepository {
    fun save(match: Match): Match
    fun findById(id: Long): Match
    fun findAllByPeriod(makerMemberId: Long, candidateId: Long, today: LocalDateTime, tomorrow: LocalDateTime): List<Match>
    fun findByCandidates(makerMemberId: Long, leftCandidateId: Long, rightCandidateId: Long): Match?
}
