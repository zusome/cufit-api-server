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
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class AuthorizationMemberRepositoryAdapter(
    private val memberService: MemberService,
    private val memberAuthorizationJpaRepository: MemberAuthorizationJpaRepository
) : AuthorizationMemberRepository {

    override fun register(authorizationMember: AuthorizationMember): AuthorizationMember {
        return toDomain(memberService.register(registerCommand(authorizationMember)))
    }

    override fun findById(memberId: Long): AuthorizationMember {
        return toDomain(memberService.findById(memberId))
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

    private fun toDomain(it: MemberEntity) =
        AuthorizationMember(
            username = it.name,
            email = it.email,
            providerId = it.memberAuthorization.providerId,
            provider = Provider.of(it.memberAuthorization.provider),
            authority = Authority.of(it.memberType.name),
            memberId = it.id ?: throw RuntimeException()
        )
}
