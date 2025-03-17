package com.official.cufitapi.domain.auth.domain.repository

@FunctionalInterface
interface OidcProviders {
    fun findByIdToken(idToken: String, provider: String): String
}
