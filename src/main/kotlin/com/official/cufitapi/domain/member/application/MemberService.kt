package com.official.cufitapi.domain.member.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.invitation.infrastructure.persistence.InvitationCardJpaRepository
import com.official.cufitapi.domain.member.application.command.UpdateMemberProfileCommand
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberAuthorizationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository,
    private val invitationCardJpaRepository: InvitationCardJpaRepository
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
        memberType = MemberType.ofName(memberRegisterCommand.authority)
    )

    @Transactional(readOnly = true)
    fun findById(memberId: Long): MemberEntity = memberJpaRepository.findById(memberId)
        .orElseThrow { NotFoundException("존재하지 않는 사용자") }

    @Transactional
    fun updateRealName(command: UpdateMemberProfileCommand): MemberEntity {
        val member = memberJpaRepository.findByIdOrNull(command.memberId) ?: throw InvalidRequestException("존재하지 않는 사용자 id: $command")
        member.updateName(name = command.name)
        return member
    }

    // 사용자 전화번호 인증
    fun validatePhoneNumber() {
        // TODO : 문자 메시지로 인증번호 요청
        // TODO : 응답 cache에 저장
    }
}

data class MemberRegisterCommand(
    val name: String?,
    val email: String?,
    val provider: String,
    val providerId: String,
    val authority: String
)
