package com.official.cufitapi.domain.auth.api.dto

import com.official.cufitapi.domain.auth.domain.member.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.token.AuthorizationToken

data class OidcLoginHttpResponse(
    val accessToken: String,
    val refreshToken: String,
    val memberType: String,
) {
    constructor(authorizationMember: AuthorizationMember, authorizationToken: AuthorizationToken) : this(
        accessToken = authorizationToken.accessToken.accessToken,
        refreshToken = authorizationToken.refreshToken.refreshToken,
        memberType = authorizationMember.authority.name
    )
}
