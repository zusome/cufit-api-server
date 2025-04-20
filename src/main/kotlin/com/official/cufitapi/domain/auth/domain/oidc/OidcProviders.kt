package com.official.cufitapi.domain.auth.domain.oidc

@FunctionalInterface
interface OidcProviders {
    fun findByIdToken(idToken: String, provider: String): String
}
