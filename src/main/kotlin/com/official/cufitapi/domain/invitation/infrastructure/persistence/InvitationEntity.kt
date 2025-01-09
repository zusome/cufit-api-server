package com.official.cufitapi.domain.invitation.infrastructure.persistence

import com.official.cufitapi.domain.member.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/*
   초대 Table
 */
@Entity
@Table(name = "invitation")
class InvitationEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    /*
    초대 code
    */
    val code: String,
    /*
    초대하는 사람과의 관계
    */
    val relationType: MatchMakerCandidateRelationType,
    /*
    초대하는 사용자 ID
    */
    val senderId: Long,
    /*
    사용 여부
    : 초대코드는 사용되면, 삭제 되어야하지만 이후에 추적을 위해서, Soft Delete
    */
    var isActivated: Boolean = true

) : BaseTimeEntity() {

    fun deactivate() {
        isActivated = false;
    }
}