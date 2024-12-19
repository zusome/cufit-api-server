package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.Gender
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
    var name: String,

    /*
    사용자 출생 연도
    */
    var yearOfBirth: Int,

    /*
    사용자 email
    */
    val email: String,

    /*
    성별
    */
    @Enumerated(value = EnumType.STRING)
    var gender: Gender,

    /*
    전화번호
    */
    var phoneNumber: String? = null,

    ) : BaseTimeEntity() {
}