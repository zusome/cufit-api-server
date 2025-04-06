package com.official.cufitapi.domain.arrangement.domain

import java.time.LocalDateTime

interface ArrangementRepository {
    fun save(arrangement: Arrangement): Arrangement
    fun findById(id: Long): Arrangement
    fun findAllByPeriod(matchMakerId: Long, candidateId: Long, today: LocalDateTime, tomorrow: LocalDateTime): List<Arrangement>
    fun findByCandidates(matchMakerId: Long, leftCandidateId: Long, rightCandidateId: Long): Arrangement?
}
