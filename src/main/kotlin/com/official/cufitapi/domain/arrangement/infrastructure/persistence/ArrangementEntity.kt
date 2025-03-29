package com.official.cufitapi.domain.arrangement.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Table(name = "arrangements")
@Entity
class ArrangementEntity(

    @Comment("주선 신청자")
    @Column(name = "match_maker_member_id", unique = false, nullable = false, updatable = false)
    var matchMakerMemberId: Long,

    @Comment("첫번째 주선 대상자")
    @Column(name = "left_candidate_member_id", unique = false, nullable = false, updatable = false)
    var leftCandidateMemberId: Long,

    @Comment("두번째 주선 대상자")
    @Column(name = "right_candidate_member_id", unique = false, nullable = false, updatable = false)
    var rightCandidateId: Long,

    @Comment("주선 상태")
    @Column(name = "arrangement_status", unique = false, nullable = false, updatable = true)
    var arrangementStatus: Int,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
): BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArrangementEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
