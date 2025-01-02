package com.official.cufitapi.domain.auth.domain.repository

@FunctionalInterface
interface OidcProviderIdClient {
    fun findByIdToken(idToken: String, provider: String): String
}