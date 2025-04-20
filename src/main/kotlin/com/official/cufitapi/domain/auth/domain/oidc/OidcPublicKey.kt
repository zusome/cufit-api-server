package com.official.cufitapi.domain.auth.domain.oidc

import com.official.cufitapi.domain.auth.domain.oidc.vo.OidcPublicKeyId

data class OidcPublicKey(
    val kid: String,
    val alg: String,
    val kty: String,
    val use: String,
    val n: String,
    val e: String
) {
    fun match(oidcPublicKeyId: OidcPublicKeyId): Boolean {
        return kid == oidcPublicKeyId.kid && alg == oidcPublicKeyId.alg
    }
}
