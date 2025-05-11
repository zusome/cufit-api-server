package com.official.cufitapi.domain.auth.infrastructure.persist

import org.springframework.data.jpa.repository.JpaRepository

interface JpaSmsAuthenticationRepository : JpaRepository<JpaSmsAuthentication, Long> {
    fun findAll(memberId: Long): List<JpaSmsAuthentication>
}
