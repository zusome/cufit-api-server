package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.NotificationType
import jakarta.persistence.*

/*
   알림 Table
 */
@Entity
@Table(name = "notification")
class NotificationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String,
    val memberId: Long,
    @Enumerated(EnumType.STRING)
    val notificationType: NotificationType
): BaseTimeEntity() {
}