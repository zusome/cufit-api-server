package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.domain.auth.domain.AuthorizationMember

data class AuthorizationTokenCreationCommand(
    val memberId: Long,
    val authority: String
) {
    constructor(authorizationMember: AuthorizationMember): this(
        memberId = authorizationMember.memberId ?: throw RuntimeException(),
        authority = authorizationMember.authority.name
    )
}
