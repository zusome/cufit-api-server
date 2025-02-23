package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import com.official.cufitapi.domain.member.domain.vo.MemberType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

/*
   사용자 Table
 */
@Entity
@Table(name = "member")
class MemberEntity(

    @Column(name = "name", unique = false, nullable = true)
    @Comment("사용자 이름")
    var name: String? = null,

    @Column(name = "email", unique = true, nullable = true)
    @Comment("사용자 이메일")
    var email: String? = null,

    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberAuthorization: MemberAuthorizationEntity,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "authority", unique = false, nullable = false)
    var memberType: MemberType,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

) : BaseTimeEntity() {
    init {
        memberAuthorization.member = this
    }

    fun updateName(name: String) {
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
