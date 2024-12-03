package com.official.cufitapi.domain.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/*
   주선자 Table
 */
@Entity
class MatchMaker(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {
}