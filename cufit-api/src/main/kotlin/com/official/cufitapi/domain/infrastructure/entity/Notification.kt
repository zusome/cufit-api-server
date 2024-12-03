package com.official.cufitapi.domain.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/*
   알림 Table
 */
@Entity
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val content: String,
    val memberId: Long
): BaseTimeEntity() {
}