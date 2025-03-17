package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMatchMakerRepository : JpaRepository<JpaMatchMaker, Long> {
    fun findByMemberId(memberId: Long) : JpaMatchMaker?
}
