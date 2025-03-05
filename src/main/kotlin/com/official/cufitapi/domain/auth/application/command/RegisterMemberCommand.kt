package com.official.cufitapi.domain.auth.application.command

import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.vo.Authority

data class RegisterMemberCommand(
    val username: String? = null,
    val email: String? = null,
    val provider: String,
    val providerId: String,
) {
    fun toDomain(): AuthorizationMember = AuthorizationMember(
        provider = this.provider,
        providerId = this.providerId,
        authority = Authority.BASIC,
        username = this.username,
        email = this.email
    )
}
