package com.official.cufitapi.domain.auth.domain

import com.official.cufitapi.domain.auth.domain.vo.Authority
import com.official.cufitapi.domain.auth.domain.vo.Provider

class AuthorizationMember(
    val providerId: String,
    val provider: Provider,
    val authority: Authority,
    val username: String?,
    val email: String?,
    val memberId: Long? = null
) {
    constructor(provider: String, providerId: String, authority: Authority, username: String?, email: String?) : this(
        providerId = providerId,
        provider = Provider.of(provider),
        authority = authority,
        username = username,
        email = email,
    )

    fun isSameAuthority(authority: String): Boolean =
        this.authority.isSameAuthority(authority)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthorizationMember

        if (providerId != other.providerId) return false
        if (provider != other.provider) return false
        if (memberId != other.memberId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = providerId.hashCode()
        result = 31 * result + provider.hashCode()
        result = 31 * result + (memberId?.hashCode() ?: 0)
        return result
    }
}
