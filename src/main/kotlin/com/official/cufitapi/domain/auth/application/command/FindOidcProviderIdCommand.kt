package com.official.cufitapi.domain.auth.application.command

data class FindOidcProviderIdCommand(
    val idToken: String,
    val provider: String,
)
