package com.official.cufitapi.domain.match.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Table(name = "matches")
@Entity
class JpaMatch(

    @Comment("주선 신청자")
    @Column(name = "maker_member_id", unique = false, nullable = false, updatable = false)
    var makerMemberId: Long,

    @Comment("첫번째 주선 대상자")
    @Column(name = "left_candidate_member_id", unique = false, nullable = false, updatable = false)
    var leftCandidateMemberId: Long,

    @Comment("두번째 주선 대상자")
    @Column(name = "right_candidate_member_id", unique = false, nullable = false, updatable = false)
    var rightCandidateId: Long,

    @Comment("주선 상태")
    @Column(name = "status", unique = false, nullable = false, updatable = true)
    var status: Int,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
): BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JpaMatch

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
