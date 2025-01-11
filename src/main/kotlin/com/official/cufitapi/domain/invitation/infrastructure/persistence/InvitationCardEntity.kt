package com.official.cufitapi.domain.invitation.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

/*
   초대장 Table
 */
@Entity
@Table(name = "invitation_card")
class InvitationCardEntity(

    @Comment("초대 코드")
    @Column(name = "code", nullable = false, updatable = false)
    var code: String,

    @Comment("초대하는 사용자 ID")
    @Column(name = "inviter_id", nullable = false, updatable = false)
    var inviterId: Long,

    @Comment("초대하는 사람과의 관계")
    @Column(name = "relationType", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var relationType: MatchMakerCandidateRelationType,

    /**
     * 초대코드는 사용되면, 삭제 되어야하지만 이후에 추적을 위해서, Soft Delete
     */
    @Comment("사용 여부")
    @Column(name = "is_activated", nullable = false)
    var isActivated: Boolean = true,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) : BaseTimeEntity() {

    fun deactivate() {
        isActivated = false;
    }
}
