package com.official.cufitapi.domain.notification.infrastructure.persistence

import com.official.cufitapi.domain.member.enums.NotificationType
import com.official.cufitapi.domain.member.infrastructure.persistence.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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
) : BaseTimeEntity() {
}