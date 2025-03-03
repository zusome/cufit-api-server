package com.official.cufitapi.domain.auth.infrastructure.persist

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "authorization_tokens")
@Entity
class JpaAuthorizationToken(
    @Id
    var memberId: Long,
    var accessToken: String,
    var refreshToken: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JpaAuthorizationToken

        return memberId == other.memberId
    }

    override fun hashCode(): Int {
        return memberId.hashCode()
    }
}
