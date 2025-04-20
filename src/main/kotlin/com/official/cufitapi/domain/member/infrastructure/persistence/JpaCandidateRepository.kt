package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaCandidateRepository : JpaRepository<JpaCandidate, Long> {

    fun findByMemberId(memberId: Long): JpaCandidate?
}
