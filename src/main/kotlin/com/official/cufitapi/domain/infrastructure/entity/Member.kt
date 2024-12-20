package com.official.cufitapi.domain.infrastructure.entity

import com.official.cufitapi.domain.enums.MemberType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import org.hibernate.annotations.Comment

/*
   사용자 Table
 */
@Entity
class Member(

    @Column(name = "name", unique = false, nullable = false)
    @Comment("사용자 이름")
    var name: String,

    @Column(name = "email", unique = true, nullable = true)
    @Comment("사용자 이메일")
    var email: String,

    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberAuthorization: MemberAuthorization,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "authority", unique = false, nullable = false)
    var memberType: MemberType,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

) : BaseTimeEntity() {
    init {
        memberAuthorization.member = this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
