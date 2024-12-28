package com.official.cufitapi.domain.infrastructure.persistence

import com.official.cufitapi.domain.enums.Gender
import com.official.cufitapi.domain.enums.MemberType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


/*
   후보자 Table
 */
@Entity
@Table(name = "match_candidate")
class MatchCandidateEntity(
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
    var isMatchingAgreed: Boolean = true,

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


    ) {

    fun deactivateMatching() {
        isMatchingAgreed = false
    }
}