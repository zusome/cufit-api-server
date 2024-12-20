package com.official.cufitapi.auth.domain.repository

import com.official.cufitapi.auth.domain.AuthorizationMember

@FunctionalInterface
fun interface AuthorizationMemberRepository {
    fun register(authorizationMember: AuthorizationMember): AuthorizationMember
}