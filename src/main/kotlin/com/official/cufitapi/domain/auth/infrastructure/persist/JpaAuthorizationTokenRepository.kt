package com.official.cufitapi.domain.auth.infrastructure.persist

import org.springframework.data.jpa.repository.JpaRepository

interface JpaAuthorizationTokenRepository : JpaRepository<JpaAuthorizationToken, Long> {
    fun findByMemberId(memberId: Long): JpaAuthorizationToken?
}
