package com.official.cufitapi.domain.connection.infrastructure.persistence

import com.official.cufitapi.domain.connection.domain.vo.MatchStatus
import org.springframework.data.jpa.repository.JpaRepository

interface MatchConnectionJpaRepository : JpaRepository<MatchConnectionEntity, Long> {

    fun findAllByReceiverIdAndStatusOrderByCreatedDate(
        receiverId: Long,
        status: MatchStatus
    ): List<MatchConnectionEntity>
}