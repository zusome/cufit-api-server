package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.domain.member.enums.MatchStatus
import org.springframework.data.jpa.repository.JpaRepository

interface MatchConnectionJpaRepository : JpaRepository<MatchConnectionEntity, Long> {

    fun findAllByReceiverIdAndStatusOrderByCreatedDate(
        receiverId: Long,
        status: MatchStatus
    ): List<MatchConnectionEntity>
}