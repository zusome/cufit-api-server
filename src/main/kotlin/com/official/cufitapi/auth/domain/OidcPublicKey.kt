package com.official.cufitapi.auth.domain

import com.official.cufitapi.auth.domain.vo.OidcPublicKeyId

class OidcPublicKey(
    val publicKeyId: OidcPublicKeyId,
    val kty: String,
    val use: String,
    val n: String,
    val e: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OidcPublicKey

        return publicKeyId == other.publicKeyId
    }

    override fun hashCode(): Int {
        return publicKeyId.hashCode()
    }
}