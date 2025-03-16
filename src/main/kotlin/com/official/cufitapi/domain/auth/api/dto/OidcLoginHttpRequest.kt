package com.official.cufitapi.domain.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.official.cufitapi.domain.auth.application.command.RegisterAuthorizationMemberCommand

data class OidcLoginHttpRequest(

    @JsonProperty("userIdentifier")
    val userIdentifier: String,

    @JsonProperty("username")
    val username: String = "큐피트",

    @JsonProperty("email")
    val email: String = "",

    @JsonProperty("provider")
    val provider: String,
) {
    fun toCommand(providerId: String): RegisterAuthorizationMemberCommand =
        RegisterAuthorizationMemberCommand(username, email, provider, providerId)
}


