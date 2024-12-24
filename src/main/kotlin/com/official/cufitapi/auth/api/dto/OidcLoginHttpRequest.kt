package com.official.cufitapi.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OidcLoginHttpRequest(
    @JsonProperty("username")
    val username: String = "큐피트",

    @JsonProperty("email")
    val email: String = "",

    @JsonProperty("provider")
    val provider: String,
)


