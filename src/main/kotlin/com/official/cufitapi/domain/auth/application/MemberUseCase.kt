package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.RegisterMemberCommand
import com.official.cufitapi.domain.auth.domain.AuthorizationMember

interface RegisterMemberUseCase {
    fun register(idToken: RegisterMemberCommand): AuthorizationMember
}

interface FindMemberUseCase {
    fun findById(memberId: Long): AuthorizationMember
}
