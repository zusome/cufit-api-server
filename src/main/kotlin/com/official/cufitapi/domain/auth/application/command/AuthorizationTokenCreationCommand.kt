package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.vo.Authority

data class AuthorizationTokenCreationCommand(
    val memberId: Long,
    val authority: Authority
) {
    constructor(authorizationMember: AuthorizationMember): this(
        memberId = authorizationMember.memberId ?: throw RuntimeException(),
        authority = authorizationMember.authority
    )
}
