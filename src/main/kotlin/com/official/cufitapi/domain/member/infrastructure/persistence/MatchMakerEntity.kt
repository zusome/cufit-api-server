package com.official.cufitapi.domain.member.infrastructure.persistence

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "match_maker")
class MatchMakerEntity(
    @Comment("고객")
    @JoinColumn(name = "member_id")
    @OneToOne
    var member: MemberEntity,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)