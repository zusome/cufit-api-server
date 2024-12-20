package com.official.cufitapi.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.official.cufitapi.auth.domain.AuthorizationMember
import com.official.cufitapi.auth.domain.AuthorizationToken

data class OidcLoginHttpResponse(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("refresh_token")
    val refreshToken: String,

    @JsonProperty("authority")
    val authority: String
) {
    constructor(authorizationMember: AuthorizationMember, authorizationToken: AuthorizationToken) : this(
        accessToken = authorizationToken.accessToken.accessToken,
        refreshToken = authorizationToken.refreshToken.refreshToken,
        authority = authorizationMember.authority.name
    )
}
