package com.official.cufitapi.domain.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/*
   초대 Table
 */
@Entity
class Invitation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val code: String,
    // 사용자 id
    val memberId: Long
) : BaseTimeEntity() {
}