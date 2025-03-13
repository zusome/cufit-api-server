package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.RegisterAuthorizationMemberCommand
import com.official.cufitapi.domain.auth.domain.AuthorizationMember

interface RegisterAuthorizationMemberUseCase {
    fun register(idToken: RegisterAuthorizationMemberCommand): AuthorizationMember
}

interface FindAuthorizationMemberUseCase {
    fun findById(memberId: Long): AuthorizationMember
}
