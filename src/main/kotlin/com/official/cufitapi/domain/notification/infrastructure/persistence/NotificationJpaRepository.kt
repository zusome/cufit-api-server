package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationJpaRepository : JpaRepository<NotificationEntity, Long> {
    @Query(
        value = """
            SELECT * 
            FROM notification 
            WHERE member_id = :memberId 
            ORDER BY created_date DESC
        """,
        nativeQuery = true
    )
    fun findAllByMemberIdOrderByCreatedDateDesc(@Param("memberId") memberId: Long): List<NotificationEntity>
}