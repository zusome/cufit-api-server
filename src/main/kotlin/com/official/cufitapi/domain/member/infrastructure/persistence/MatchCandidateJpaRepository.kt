package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MatchCandidateJpaRepository : JpaRepository<MatchCandidateEntity, Long> {

    fun findByMemberId(memberId: Long): MatchCandidateEntity?
}
