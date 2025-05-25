package com.official.cufitapi.domain.invitation.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

/*
   초대장 Table
 */
@Entity
@Table(name = "invitation_cards")
class JpaInvitationCard(

    @Comment("초대 코드")
    @Column(name = "code", nullable = false, updatable = false)
    var code: String,

    @Comment("초대 유형")
    @Column(name = "invitation_type", nullable = false, updatable = false)
    var invitationType: String,

    @Comment("초대하는 사용자 ID")
    @Column(name = "inviter_id", nullable = false, updatable = false)
    var inviterId: Long,

    @Comment("초대하는 사람과의 관계")
    @Column(name = "relationType", nullable = false, updatable = false)
    var relationType: String,

    @Comment("수락 여부")
    @Column(name = "is_accepted", nullable = false)
    var isAccepted: Boolean = false,

    @Comment("초대받는 사용자 ID")
    @Column(name = "invitee_id", nullable = true, updatable = true)
    var inviteeId: Long? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) : BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JpaInvitationCard

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
