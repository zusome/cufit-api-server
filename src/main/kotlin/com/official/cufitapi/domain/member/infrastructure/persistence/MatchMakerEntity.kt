package com.official.cufitapi.domain.member.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/*
   주선자 Table
 */
@Entity
@Table(name = "match_maker")
class MatchMakerEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    /*
       사용자 id
    */
    val memberId: Long
) {
}