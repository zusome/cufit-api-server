package com.official.cufitapi.domain.auth.application.command

data class OidcProviderIdFindCommand(
    val idToken: String,
    val provider: String,
)
