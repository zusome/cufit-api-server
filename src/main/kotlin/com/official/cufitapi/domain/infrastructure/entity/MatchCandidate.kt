package com.official.cufitapi.domain.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


/*
   후보자 Table
 */
@Entity
class MatchCandidate(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    /*
     사용자 id
    */
    val memberId: Long,

    /*
     이상형 정보
    */
    val idealMbti: String,
    val idealAgeRange: String,
    val idealHeightRange: String,

    /*
     키
    */
    val height: Int,

    /*
      지하철역
    */
    val station: String,
    /*
     직업
    */
    val job: String,

    /*
     매칭 동의 여부
    */
    var isMatchingAgreed: Boolean = true

) {

    fun deactivateMatching() {
        isMatchingAgreed = false
    }
}