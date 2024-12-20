package com.official.cufitapi.auth.application.command

data class OidcProviderIdFindCommand(
    val idToken: String,
    val provider: String,
)
