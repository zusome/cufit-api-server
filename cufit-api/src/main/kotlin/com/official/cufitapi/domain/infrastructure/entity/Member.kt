package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.infrastructure.MemberType
import jakarta.persistence.*

/*
   사용자 Table
 */
@Entity
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(value = EnumType.STRING)
    val currentType: MemberType
) : BaseTimeEntity() {
}