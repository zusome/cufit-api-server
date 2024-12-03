package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationJpaRepository : JpaRepository<Notification, Long> {
    fun findAllByMemberIdOrderByCreatedDateDesc(memberId: Long) : List<Notification>
}