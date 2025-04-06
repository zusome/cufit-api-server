package com.official.cufitapi.domain.match.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface JpaMatchRepository : JpaRepository<JpaMatch, Long> {
    @Query(
        """
            SELECT a
            FROM JpaMatch a
            WHERE a.makerMemberId = :makerMemberId
            AND (a.leftCandidateMemberId = :candidateId or a.rightCandidateId = :candidateId)
            AND a.createdDate BETWEEN :today AND :tomorrow
        """
    )
    fun findAllByPeriod(makerMemberId: Long, candidateId: Long, today: LocalDateTime, tomorrow: LocalDateTime): List<JpaMatch>

    @Query(
        """
            SELECT a
            FROM JpaMatch a
            WHERE a.makerMemberId = :makerMemberId
            AND (
                    (a.leftCandidateMemberId = :leftCandidateId and a.rightCandidateId = :rightCandidateId)
                    or 
                    (a.leftCandidateMemberId = :rightCandidateId and a.rightCandidateId = :leftCandidateId)
            )
        """
    )
    fun findByCandidates(makerMemberId: Long, leftCandidateId: Long, rightCandidateId: Long): JpaMatch?
}
