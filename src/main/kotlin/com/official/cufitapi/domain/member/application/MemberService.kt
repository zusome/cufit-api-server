package com.official.cufitapi.domain.member.application

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.invitation.infrastructure.persistence.JpaInvitationCardRepository
import com.official.cufitapi.domain.member.application.command.UpdateMemberProfileCommand
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberAuthorizationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


interface UpdateAuthorityMemberUseCase {
    fun updateMatchMaker(memberId: Long)
    fun updateMatchCandidate(memberId: Long)
}

@Service
class MemberService(
    private val memberJpaRepository: MemberJpaRepository,
): UpdateAuthorityMemberUseCase {

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
        .orElseThrow { NotFoundException(ErrorCode.NOT_FOUND_MEMBER) }

    @Transactional
    fun updateRealName(command: UpdateMemberProfileCommand): MemberEntity {
        val member = memberJpaRepository.findByIdOrNull(command.memberId) ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateName(name = command.name)
        return member
    }

    // 사용자 전화번호 인증
    fun validatePhoneNumber() {
        // TODO : 문자 메시지로 인증번호 요청
        // TODO : 응답 cache에 저장
    }

    @Transactional(readOnly = false)
    override fun updateMatchMaker(memberId: Long) {
        val member = memberJpaRepository.findByIdOrNull(memberId) ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateMatchMaker()
    }

    @Transactional(readOnly = false)
    override fun updateMatchCandidate(memberId: Long) {
        val member = memberJpaRepository.findByIdOrNull(memberId) ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)
        member.updateMatchCandidate()
    }
}

data class MemberRegisterCommand(
    val name: String?,
    val email: String?,
    val provider: String,
    val providerId: String,
    val authority: String
)
