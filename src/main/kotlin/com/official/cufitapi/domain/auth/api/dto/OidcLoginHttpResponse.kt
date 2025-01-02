package com.official.cufitapi.domain.auth.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.AuthorizationToken

data class OidcLoginHttpResponse(
    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("member_type")
    val memberType: String
) {
    constructor(authorizationMember: AuthorizationMember, authorizationToken: AuthorizationToken) : this(
        accessToken = authorizationToken.accessToken.accessToken,
        memberType = authorizationMember.authority.name
    )
}
