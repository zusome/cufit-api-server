package com.official.cufitapi.auth.infrastructure

import com.official.cufitapi.auth.domain.AuthorizationMember
import com.official.cufitapi.auth.domain.repository.AuthorizationMemberRepository
import com.official.cufitapi.auth.domain.vo.Authority
import com.official.cufitapi.auth.domain.vo.Provider
import com.official.cufitapi.domain.application.MemberRegisterCommand
import com.official.cufitapi.domain.application.MemberService
import org.springframework.stereotype.Component

@Component
class AuthorizationMemberRepositoryAdapter(
    private val memberService: MemberService
): AuthorizationMemberRepository {

    override fun register(authorizationMember: AuthorizationMember): AuthorizationMember =
        memberService.register(registerCommand(authorizationMember))
            .let { AuthorizationMember(
                    name = it.name,
                    email = it.email,
                    providerId = it.memberAuthorization.providerId,
                    provider = Provider.of(it.memberAuthorization.provider),
                    authority = Authority.of(it.memberType.name),
                    memberId = it.id ?: throw RuntimeException()
                )
            }

    private fun registerCommand(authorizationMember: AuthorizationMember) =
        MemberRegisterCommand(
            name = authorizationMember.name,
            email = authorizationMember.email,
            provider = authorizationMember.provider.provider,
            providerId = authorizationMember.providerId,
            authority = authorizationMember.authority.name
        )
}
