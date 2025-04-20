package com.official.cufitapi.domain.auth.domain.member

interface AuthorizationMemberRepository {
    fun register(authorizationMember: AuthorizationMember): AuthorizationMember
    fun findById(memberId: Long): AuthorizationMember
}
