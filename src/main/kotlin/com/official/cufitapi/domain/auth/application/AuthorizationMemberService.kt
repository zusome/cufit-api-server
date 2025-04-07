package com.official.cufitapi.domain.auth.application

import com.official.cufitapi.domain.auth.application.command.RegisterAuthorizationMemberCommand
import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationMemberRepository
import org.springframework.stereotype.Service

interface RegisterAuthorizationMemberUseCase {
    fun register(idToken: RegisterAuthorizationMemberCommand): AuthorizationMember
}

interface FindAuthorizationMemberUseCase {
    fun findById(memberId: Long): AuthorizationMember
}

@Service
class AuthorizationMemberService(
    private val authorizationMemberRepository: AuthorizationMemberRepository,
) : RegisterAuthorizationMemberUseCase, FindAuthorizationMemberUseCase {

    override fun register(command: RegisterAuthorizationMemberCommand): AuthorizationMember {
        return authorizationMemberRepository.register(command.toDomain())
    }

    override fun findById(memberId: Long): AuthorizationMember {
        return authorizationMemberRepository.findById(memberId)
    }
}
