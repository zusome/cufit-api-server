package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

/*
   사용자 Table
 */
@Table(name = "member_authorization")
@Entity
class MemberAuthorizationEntity(

    /*
    OAuth member ID
     */
    @Column(name = "provider_id", unique = true, nullable = false, updatable = false)
    val providerId: String,

    /*
    OAuth 제공자
     */
    @Column(name = "provider", unique = false, nullable = false, updatable = false)
    val provider: String,

    @JoinColumn(name = "member_id")
    @OneToOne
    var member: MemberEntity? = null,

    /*
    SMS 인증 코드
     */
    var smsAuthCode: String? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

) : BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberAuthorizationEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
