package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMatchCandidateRepository : JpaRepository<JpaMatchCandidate, Long> {

    fun findByMemberId(memberId: Long): JpaMatchCandidate?
}
