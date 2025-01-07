package com.official.cufitapi.domain.auth.domain.vo

data class OidcPublicKeyId(
    val kid: String,
    val alg: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OidcPublicKeyId

        if (kid != other.kid) return false
        if (alg != other.alg) return false

        return true
    }

    override fun hashCode(): Int {
        var result = kid.hashCode()
        result = 31 * result + alg.hashCode()
        return result
    }
}