package com.official.cufitapi.domain.arrangement.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ArrangementJpaRepository : JpaRepository<ArrangementEntity, Long> {
    @Query(
        """
            SELECT count(1)
            FROM ArrangementEntity a
            WHERE a.matchMakerMemberId = :matchMakerId
            AND (a.leftCandidateMemberId = :candidateId or a.rightCandidateId = :candidateId)
            AND a.createdDate BETWEEN :today AND :tomorrow
        """
    )
    fun todayCount(matchMakerId: Long, candidateId: Long, today: LocalDateTime, tomorrow: LocalDateTime): Long

    @Query(
        """
            SELECT count(1)
            FROM ArrangementEntity a
            WHERE a.matchMakerMemberId = :matchMakerId
            AND (
                    (a.leftCandidateMemberId = :leftCandidateId and a.rightCandidateId = :rightCandidateId)
                    or 
                    (a.leftCandidateMemberId = :rightCandidateId and a.rightCandidateId = :leftCandidateId)
            )
        """
    )
    fun existsCandidates(matchMakerId: Long, leftCandidateId: Long, rightCandidateId: Long): Boolean
}
