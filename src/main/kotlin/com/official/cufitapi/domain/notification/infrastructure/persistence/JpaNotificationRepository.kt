package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaNotificationRepository: JpaRepository<JpaNotification, Long> {
    @Query(
        value = """
            SELECT * 
            FROM notifications
            WHERE receiver_id = :receiverId
        """,
        nativeQuery = true
    )
    fun findAllByMemberIdOrderByCreatedDateDesc(@Param("receiverId") receiverId: Long): List<JpaNotification>
}
