package com.official.cufitapi.domain.infrastructure.persistence

import com.official.cufitapi.domain.enums.MatchStatus
import org.springframework.data.jpa.repository.JpaRepository

interface MatchConnectionJpaRepository : JpaRepository<MatchConnectionEntity, Long> {

    fun findAllByReceiverIdAndStatusOrderByCreatedDate(receiverId: Long, status: MatchStatus) : List<MatchConnectionEntity>


}