package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationJpaRepository : JpaRepository<Notification, Long> {
    @Query(
        value = """
            SELECT * 
            FROM notification 
            WHERE member_id = :memberId 
            ORDER BY created_date DESC
        """,
        nativeQuery = true
    )
    fun findAllByMemberIdOrderByCreatedDateDesc(@Param("memberId") memberId: Long): List<Notification>
}