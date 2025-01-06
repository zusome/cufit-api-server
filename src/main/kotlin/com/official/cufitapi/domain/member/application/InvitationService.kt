package com.official.cufitapi.domain.member.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeGenerationCommand
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeValidationCommand
import com.official.cufitapi.domain.member.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.enums.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.InvitationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.InvitationJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

interface InvitationTokenValidationUseCase {
    fun validate(command: InvitationCodeValidationCommand) : MemberType
}

interface InvitationTokenGenerationUseCase {
    fun generate(invitationCodeGenerateCommand: InvitationCodeGenerationCommand): com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode
}

@Service
@Transactional(readOnly = true)
class InvitationService(
    private val invitationJpaRepository: InvitationJpaRepository,
    private val memberJpaRepository: MemberJpaRepository
) : InvitationTokenGenerationUseCase, InvitationTokenValidationUseCase {

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }

    @Transactional
    override fun validate(command: InvitationCodeValidationCommand): MemberType {
        val memberId = command.memberId
        val invitationCode = command.invitationCode.code
        val member =
            memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("존재하지 않는 사용자 id : $memberId")
        if (MemberType.invitationCodePrefix(member.memberType) != invitationCode.substring(0, 2)) {
            throw InvalidRequestException("잘못된 사용자 초대코드")
        }

        if (!invitationJpaRepository.existsBySenderIdAndCodeAndIsActivatedIsTrue(memberId, invitationCode)) {
            throw InvalidRequestException("잘못된 사용자 초대코드")
        }

        // 검증 성공하면, 초대코드 Soft Delete
        val invitation =
            invitationJpaRepository.findByCode(invitationCode) ?: throw InvalidRequestException("유효하지 않은 초대 코드")
        invitation.deactivate()

        val invitee = memberJpaRepository.findByIdOrNull(invitation.senderId) ?: throw InvalidRequestException("초대 보낸 사용자를 찾을 수 없음")
        return member.memberType
    }

    @Transactional
    override fun generate(command: InvitationCodeGenerationCommand): com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode {
        val memberId = command.memberId
        val sender =
            memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("잘못된 사용자 id 요청 : $memberId")
        val invitationCodePrefix = MemberType.invitationCodePrefix(command.memberType)
        val invitationCodeSuffix = MatchMakerCandidateRelationType.invitationCodeSuffix(command.relationType)
        val invitationCode = invitationCodePrefix + generateRandomBase62String() + invitationCodeSuffix
        val invitation = invitationJpaRepository.save(
            InvitationEntity(
                code = invitationCode,
                relationType = command.relationType,
                senderId = sender.id!!
            )
        )
        return com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode(invitation.code)
    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }
}