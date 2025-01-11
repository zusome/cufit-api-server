package com.official.cufitapi.domain.invitation.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.invitation.application.command.InvitationCodeGenerationCommand
import com.official.cufitapi.domain.invitation.application.command.InvitationCodeValidationCommand
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.infrastructure.persistence.InvitationEntity
import com.official.cufitapi.domain.invitation.infrastructure.persistence.InvitationJpaRepository
// 의존성 개선 필요
import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

interface InvitationTokenGenerationUseCase {
    fun generate(invitationCodeGenerateCommand: InvitationCodeGenerationCommand): InvitationCode
}

interface InvitationTokenValidationUseCase {
    fun validate(command: InvitationCodeValidationCommand): MemberType
}

@Service
@Transactional(readOnly = true)
class InvitationService(
    private val invitationJpaRepository: InvitationJpaRepository,
    private val memberJpaRepository: MemberJpaRepository,
    private val memberRelationJpaRepository: MemberRelationJpaRepository,
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) : InvitationTokenGenerationUseCase, InvitationTokenValidationUseCase {

    @Transactional
    override fun generate(command: InvitationCodeGenerationCommand): InvitationCode {
        val memberId = command.memberId
        val memberType = command.memberType
        val sender =
            memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("잘못된 사용자 id 요청 : $memberId")
        val invitationCodePrefix = memberType.code
        val invitationCodeSuffix = MatchMakerCandidateRelationType.invitationCodeSuffix(command.relationType)
        val invitationCode = invitationCodePrefix + generateRandomBase62String() + invitationCodeSuffix
        val invitation = invitationJpaRepository.save(
            InvitationEntity(
                code = invitationCode,
                relationType = command.relationType,
                senderId = sender.id!!
            )
        )
        return InvitationCode(invitation.code)
    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }

    @Transactional
    override fun validate(command: InvitationCodeValidationCommand): MemberType {
        val memberId = command.memberId
        val invitationCode = command.invitationCode.code
        if (!invitationJpaRepository.existsBySenderIdAndCodeAndIsActivatedIsTrue(memberId, invitationCode)) {
            throw InvalidRequestException("잘못된 사용자 초대코드")
        }
        val invitation = invitationJpaRepository.findByCode(invitationCode) ?: throw InvalidRequestException("유효하지 않은 초대 코드")
        invitation.deactivate() // 초대 코드 사용 완료

        val inviter = memberJpaRepository.findByIdOrNull(invitation.senderId) ?: throw InvalidRequestException("초대 보낸 사용자를 찾을 수 없음")
        val invitee = memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("존재하지 않는 사용자 id : $memberId")
        val memberType = MemberType.ofCode(invitationCode.substring(0, 2))
        val memberRelationEntity = MemberRelationEntity(
            inviterId = inviter.id!!,
            inviteeId = invitee.id!!,
            relationType = invitation.relationType
        )
        memberRelationJpaRepository.save(memberRelationEntity)
        invitee.memberType = memberType
        when (invitee.memberType) {
            MemberType.CANDIDATE -> { matchCandidateJpaRepository.save(MatchCandidateEntity(member = invitee)) }
            MemberType.MATCHMAKER -> { matchMakerJpaRepository.save(MatchMakerEntity(member = invitee)) }
            else -> {}
        }
        return memberType
    }

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }
}