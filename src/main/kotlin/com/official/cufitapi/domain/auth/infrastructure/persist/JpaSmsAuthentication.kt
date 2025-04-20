package com.official.cufitapi.domain.auth.infrastructure.persist

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "sms_authentications")
@Entity
class JpaSmsAuthentication(
    var phone: String,
    var code: String,
    var memberId: Long,
    var isVerified: Boolean = false,
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JpaSmsAuthentication

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
