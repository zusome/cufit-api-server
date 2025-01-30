package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MatchCandidateJpaRepository : JpaRepository<MatchCandidateEntity, Long> {

    fun findByMemberId(memberId: Long): MatchCandidateEntity?

    @Query("SELECT e FROM MatchCandidateEntity e WHERE e.member.id IN :memberIds")
    fun findAllByMemberIdIn(memberIds: List<Long>): List<MatchCandidateEntity>

    @Query("SELECT e FROM MatchCandidateEntity e WHERE e.member.id NOT IN :memberIds")
    fun findAllByMemberIdNotIn(memberIds: List<Long>): List<MatchCandidateEntity>
}
