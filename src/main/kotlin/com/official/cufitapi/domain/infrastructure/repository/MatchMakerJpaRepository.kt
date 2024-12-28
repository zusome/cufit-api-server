package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MatchMakerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MatchMakerJpaRepository : JpaRepository<MatchMakerEntity, Long> {
    fun findByMemberId(memberId: Long) : MatchMakerEntity?
}