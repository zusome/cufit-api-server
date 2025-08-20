package com.official.cufitapi.domain.auth.domain.oidc

@FunctionalInterface
interface OidcProviders {
    fun providerId(idToken: String, provider: String): String
}
