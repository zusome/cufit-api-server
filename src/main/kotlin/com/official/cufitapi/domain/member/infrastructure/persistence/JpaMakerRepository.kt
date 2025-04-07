package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMakerRepository : JpaRepository<JpaMaker, Long> {
    fun findByMemberId(memberId: Long) : JpaMaker?
}
