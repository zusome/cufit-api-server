package com.official.cufitapi.domain.member.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.member.api.dto.MemberInfoResponse
import com.official.cufitapi.domain.member.api.dto.MemberProfileRequest
import com.official.cufitapi.domain.member.enums.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.InvitationJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberAuthorizationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository,
    private val invitationJpaRepository: InvitationJpaRepository
) {

    @Transactional
    fun register(memberRegisterCommand: MemberRegisterCommand): MemberEntity {
        return memberJpaRepository.findByProviderAndProviderId(
            memberRegisterCommand.provider,
            memberRegisterCommand.providerId
        ) ?: memberJpaRepository.save(init(memberRegisterCommand))
    }

    private fun init(memberRegisterCommand: MemberRegisterCommand): MemberEntity = MemberEntity(
        name = memberRegisterCommand.name,
        email = memberRegisterCommand.email,
        memberAuthorization = MemberAuthorizationEntity(
            provider = memberRegisterCommand.provider,
            providerId = memberRegisterCommand.providerId,
        ),
        memberType = MemberType.of(memberRegisterCommand.authority)
    )

    @Transactional(readOnly = true)
    fun findById(memberId: Long): MemberEntity = memberJpaRepository.findById(memberId)
        .orElseThrow { InvalidRequestException("존재하지 않는 사용자 id: $memberId") }

    @Transactional(readOnly = true)
    fun getMemberInfo(memberId: Long): MemberInfoResponse {
        val member = memberJpaRepository.findById(memberId)
            .orElseThrow { InvalidRequestException("존재하지 않는 사용자 id: $memberId") }
        val invitation = invitationJpaRepository.findBySenderId(memberId)
            ?: throw InvalidRequestException("존재하지 않는 사용자 id: $memberId")
        val invitee = memberJpaRepository.findById(invitation.senderId)
            .orElseThrow { InvalidRequestException("존재하지 않는 사용자 id: $memberId") }

        return MemberInfoResponse(
            name = member.name,
            email = member.email,
            inviteeName = invitee.name,
            relationWithInvitee = invitation.relationType
        )
    }

    // 사용자 정보 업데이트는 후보자만 진행함.
    fun updateMemberProfile(memberId: Long, request: MemberProfileRequest) {
        // Implementation for updating member profile
        val member =
            memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("존재하지 않는 사용자 id: $memberId")
    }

    // 사용자 전화번호 인증
    fun validatePhoneNumber() {
        // TODO : 문자 메시지로 인증번호 요청
        // TODO : 응답 cache에 저장
    }
}

data class MemberRegisterCommand(
    val name: String,
    val email: String,
    val provider: String,
    val providerId: String,
    val authority: String
)