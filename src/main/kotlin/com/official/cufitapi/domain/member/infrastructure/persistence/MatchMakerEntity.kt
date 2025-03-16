package com.official.cufitapi.domain.member.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "match_maker")
class MatchMakerEntity(

    @Comment("고객")
    @Column(name = "member_id", unique = true, nullable = false)
    var memberId: Long,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
