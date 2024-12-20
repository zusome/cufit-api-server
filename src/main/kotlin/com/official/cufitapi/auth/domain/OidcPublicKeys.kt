package com.official.cufitapi.auth.domain

import com.official.cufitapi.auth.domain.vo.OidcPublicKeyId

class OidcPublicKeys(
    val keys: List<OidcPublicKey>
) {
    fun match(oidcPublicKeyId: OidcPublicKeyId): OidcPublicKey =
        keys.firstOrNull { it.equals(oidcPublicKeyId) } ?: throw RuntimeException()

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
