package com.official.cufitapi.auth.domain.repository

@FunctionalInterface
interface OidcProviderIdClient {
    fun findByIdToken(idToken: String, provider: String): String
}