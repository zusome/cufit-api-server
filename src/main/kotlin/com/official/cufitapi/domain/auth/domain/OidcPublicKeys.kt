package com.official.cufitapi.domain.auth.domain

import com.official.cufitapi.domain.auth.domain.vo.OidcPublicKeyId

data class OidcPublicKeys(
    val keys: List<OidcPublicKey>
) {
    fun match(oidcPublicKeyId: OidcPublicKeyId): OidcPublicKey =
        keys.firstOrNull { it.match(oidcPublicKeyId) } ?: throw RuntimeException()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OidcPublicKeys

        return keys == other.keys
    }

    override fun hashCode(): Int {
        return keys.hashCode()
    }
}
