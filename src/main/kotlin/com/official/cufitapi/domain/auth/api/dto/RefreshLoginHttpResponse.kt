package com.official.cufitapi.domain.auth.api.dto

import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.AuthorizationToken

data class RefreshLoginHttpResponse(
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
