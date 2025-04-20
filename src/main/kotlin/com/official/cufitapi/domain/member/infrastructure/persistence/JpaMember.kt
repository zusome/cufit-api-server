package com.official.cufitapi.domain.member.infrastructure.persistence

import com.official.cufitapi.common.jpa.BaseTimeEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
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
@Table(name = "members")
class JpaMember(

    @Column(name = "name", unique = false, nullable = true)
    @Comment("사용자 이름")
    var name: String? = null,

    @Column(name = "email", unique = true, nullable = true)
    @Comment("사용자 이메일")
    var email: String? = null,

    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var memberAuthorization: JpaMemberAuthorization,

    @Column(name = "authority", unique = false, nullable = false)
    var memberType: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

) : BaseTimeEntity() {
    init {
        memberAuthorization.member = this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JpaMember

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
