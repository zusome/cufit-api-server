package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.MemberType
import jakarta.persistence.*

/*
   사용자 Table
 */
@Entity
class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    /*
    사용자의 현재 type
    */
    @Enumerated(value = EnumType.STRING)
    val currentType: MemberType,
    /*
    사용자 이름
    */
    val name: String,

    /*
    사용자 email
    */
    val email: String,

    /*
    나를 초대한 유저의 id
    */
    val inviteeId: Long,

) : BaseTimeEntity() {
}