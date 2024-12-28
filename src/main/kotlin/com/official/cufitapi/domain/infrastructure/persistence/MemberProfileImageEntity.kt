package com.official.cufitapi.domain.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

/*
   사용자 프로필 이미지 Table
 */
@Entity
@Table(name = "member_profile_image")
class MemberProfileImageEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    /*
    파일 이름
    */
    val fileName: UUID = UUID.randomUUID(),

    /*
    image url
    */
    val imageUrl: String,

    /*
    프로필 순서
    */
    val profileOrder: Int,
    /*
    사용자 id
    */
    val memberId: Long
) {

}