package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.MatchStatus
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/*
   연결 Table
 */
@Entity
class MatchConnection(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    /*
    신청자 (MatchMaker)
    */
    val matchMakerId: Long,
    /*
    보내는 사람 (MatchCandidate)
    */
    val senderId: Long,
    /*
    받는 사람 (MatchCandidate)
    */
    val receiverId: Long,
    /*
    받는 사람 (MatchCandidate)
    */
    val status: MatchStatus = MatchStatus.PROGRESSING
) : BaseTimeEntity() {
}