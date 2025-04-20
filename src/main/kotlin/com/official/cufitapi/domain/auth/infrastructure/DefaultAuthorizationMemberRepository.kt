package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.member.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.member.AuthorizationMemberRepository
import com.official.cufitapi.domain.auth.domain.member.vo.Authority
import com.official.cufitapi.domain.auth.domain.oidc.vo.Provider
import com.official.cufitapi.domain.member.application.FindMemberUseCase
import com.official.cufitapi.domain.member.application.RegisterMemberUseCase
import com.official.cufitapi.domain.member.application.command.RegisterMemberCommand
import com.official.cufitapi.domain.member.domain.Member
import org.springframework.stereotype.Component

@Component
class DefaultAuthorizationMemberRepository(
    private val registerMemberUseCase: RegisterMemberUseCase,
    private val findMemberUseCase: FindMemberUseCase,
) : AuthorizationMemberRepository {

    override fun register(authorizationMember: AuthorizationMember): AuthorizationMember = mapToAuthorizationMember(
        registerMemberUseCase.register(
            RegisterMemberCommand(
                name = authorizationMember.username,
                email = authorizationMember.email,
                provider = authorizationMember.provider.provider,
                providerId = authorizationMember.providerId,
                authority = authorizationMember.authority.name
            )
        )
    )

    override fun findById(memberId: Long): AuthorizationMember {
        return mapToAuthorizationMember(findMemberUseCase.findById(memberId))
    }

    private fun mapToAuthorizationMember(member: Member) =
        AuthorizationMember(
            username = member.name,
            email = member.email,
            providerId = member.memberAuthorization.providerId,
            provider = Provider.of(member.memberAuthorization.provider),
            authority = Authority.of(member.memberType.name),
            memberId = member.id ?: throw RuntimeException()
        )
}
