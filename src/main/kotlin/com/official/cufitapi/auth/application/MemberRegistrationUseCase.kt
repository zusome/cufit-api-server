package com.official.cufitapi.auth.application

import com.official.cufitapi.auth.application.command.MemberRegistrationCommand
import com.official.cufitapi.auth.domain.AuthorizationMember
import com.official.cufitapi.auth.domain.repository.AuthorizationMemberRepository
import com.official.cufitapi.auth.domain.vo.Authority
import com.official.cufitapi.auth.domain.vo.Provider
import org.springframework.stereotype.Service

interface MemberRegistrationUseCase {
    fun register(idToken: MemberRegistrationCommand): AuthorizationMember
}

interface MemberFindUseCase {
    fun findById(memberId: Long): AuthorizationMember
}

@Service
class AuthorizationMemberRegistrationService(
    private val authorizationMemberRepository: AuthorizationMemberRepository
) : MemberRegistrationUseCase, MemberFindUseCase {

    override fun register(command: MemberRegistrationCommand): AuthorizationMember =
        authorizationMemberRepository.register(authorizationMember(command))

    private fun authorizationMember(command: MemberRegistrationCommand) =
        AuthorizationMember(
            username = command.username,
            email = command.email,
            provider = Provider.of(command.provider),
            providerId = command.providerId,
            authority = Authority.BASIC,
        )

    override fun findById(memberId: Long): AuthorizationMember =
        authorizationMemberRepository.findById(memberId)
}
