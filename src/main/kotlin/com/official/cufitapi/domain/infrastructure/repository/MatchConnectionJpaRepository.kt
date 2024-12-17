package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.enums.MatchStatus
import com.official.cufitapi.domain.infrastructure.entity.MatchConnection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MatchConnectionJpaRepository : JpaRepository<MatchConnection, Long> {

    fun findAllByReceiverIdAndStatusOrderByCreatedDate(receiverId: Long, status: MatchStatus) : List<MatchConnection>


}