package com.official.cufitapi.domain.member.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

/*
   주선자 - 후보자 관계 Table
 */
@Entity
@Table(name = "member_relations")
class JpaMemberRelation(

    @Comment("초대 하는 회원 ID")
    @Column(name = "inviter_id", nullable = false, updatable = false)
    var inviterId: Long,

    @Comment("초대 받은 회원 ID")
    @Column(name = "invitee_id", nullable = false, updatable = false)
    var inviteeId: Long,

    @Comment("관계 타입")
    @Column(name = "relation_type", nullable = false)
    var relationType: String,

    @Id @GeneratedValue
    var id: Long? = null,
)
