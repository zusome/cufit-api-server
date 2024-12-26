package com.official.cufitapi.auth.domain.repository

import com.official.cufitapi.auth.domain.AuthorizationMember

interface AuthorizationMemberRepository {
    fun register(authorizationMember: AuthorizationMember): AuthorizationMember
    fun findById(memberId: Long): AuthorizationMember
}