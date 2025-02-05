package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.common.exception.CufitException
import com.official.cufitapi.domain.auth.domain.AuthorizationMember
import com.official.cufitapi.domain.auth.domain.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.repository.AuthorizationMemberRepository
import com.official.cufitapi.domain.auth.domain.vo.Authority
import com.official.cufitapi.domain.auth.domain.vo.Provider
import com.official.cufitapi.domain.member.application.MemberRegisterCommand
import com.official.cufitapi.domain.member.application.MemberService
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberAuthorizationJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class AuthorizationMemberRepositoryAdapter(
    private val memberJpaRepository: MemberJpaRepository,
    private val memberService: MemberService,
    private val memberAuthorizationJpaRepository: MemberAuthorizationJpaRepository
) : AuthorizationMemberRepository {

    override fun register(authorizationMember: AuthorizationMember): AuthorizationMember =
        memberService.register(registerCommand(authorizationMember))
            .let {
                AuthorizationMember(
                    username = it.name,
                    email = it.email,
                    providerId = it.memberAuthorization.providerId,
                    provider = Provider.of(it.memberAuthorization.provider),
                    authority = Authority.of(it.memberType.name),
                    memberId = it.id ?: throw RuntimeException()
                )
            }

    override fun findById(memberId: Long): AuthorizationMember {
        val member = memberService.findById(memberId)
        return AuthorizationMember(
            username = member.name,
            email = member.email,
            providerId = member.memberAuthorization.providerId,
            provider = Provider.of(member.memberAuthorization.provider),
            authority = Authority.of(member.memberType.name),
            memberId = member.id ?: throw RuntimeException()
        )
    }

    override fun saveAuthCode(smsAuthentication: SmsAuthentication) {
        val memberAuthorization = memberAuthorizationJpaRepository.findById(smsAuthentication.memberId)
            .orElseThrow { throw CufitException("MemberAuthorization not found") }
        // TODO: 전화 번호는 어떻게 처리할지 고민.
        memberAuthorization.smsAuthCode = smsAuthentication.authCode

    }

    private fun registerCommand(authorizationMember: AuthorizationMember) =
        MemberRegisterCommand(
            name = authorizationMember.username,
            email = authorizationMember.email,
            provider = authorizationMember.provider.provider,
            providerId = authorizationMember.providerId,
            authority = authorizationMember.authority.name
        )
}
