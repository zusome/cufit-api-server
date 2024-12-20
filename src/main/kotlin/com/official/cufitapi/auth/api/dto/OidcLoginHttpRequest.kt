package com.official.cufitapi.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OidcLoginHttpRequest(
    @JsonProperty("name")
    val name: String = "큐피트",

    @JsonProperty("email")
    val email: String = "",

    @JsonProperty("id_token")
    val idToken: String,

    @JsonProperty("provider")
    val provider: String,
)

