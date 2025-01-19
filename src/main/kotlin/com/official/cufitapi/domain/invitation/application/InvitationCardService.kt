package com.official.cufitapi.domain.invitation.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.invitation.application.command.InvitationCardGenerationCommand
import com.official.cufitapi.domain.invitation.application.command.InvitationCardAcceptCommand
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCard
import com.official.cufitapi.domain.invitation.infrastructure.persistence.InvitationCardEntity
import com.official.cufitapi.domain.invitation.infrastructure.persistence.InvitationCardJpaRepository
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

interface InvitationCardGenerationUseCase {
    fun generate(command: InvitationCardGenerationCommand): InvitationCard
}

interface InvitationCardAcceptUseCase {
    fun accept(command: InvitationCardAcceptCommand): MemberType
}

@Service
@Transactional(readOnly = true)
class InvitationCardService(
    private val invitationCardJpaRepository: InvitationCardJpaRepository,
    private val memberJpaRepository: MemberJpaRepository,
    private val memberRelationJpaRepository: MemberRelationJpaRepository,
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) : InvitationCardGenerationUseCase, InvitationCardAcceptUseCase {

    @Transactional
    override fun generate(command: InvitationCardGenerationCommand): InvitationCard {
        val inviter = memberJpaRepository.findByIdOrNull(command.memberId)
            ?: throw InvalidRequestException("잘못된 사용자 id 요청 : ${command.memberId}")
        val invitationCode = invitationCode(command.memberType, command.relationType)
        val invitationCard = invitationCardJpaRepository.save(
            InvitationCardEntity(
                code = invitationCode,
                relationType = command.relationType,
                inviterId = inviter.id!!
            )
        )
        return InvitationCard(invitationCard.code)
    }

    private fun invitationCode(memberType: MemberType, relationType: MatchMakerCandidateRelationType): String {
        val invitationCodePrefix = memberType.code
        val invitationCode = generateRandomBase62String()
        val invitationCodeSuffix = MatchMakerCandidateRelationType.invitationCodeSuffix(relationType)
        return "${invitationCodePrefix}$invitationCode${invitationCodeSuffix}"
    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }

    @Transactional
    override fun accept(command: InvitationCardAcceptCommand): MemberType {
        val inviteeId = command.inviteeId
        val invitationCode = command.invitationCode.code
        val invitation = invitationCardJpaRepository.findByCode(invitationCode) ?: throw InvalidRequestException("유효하지 않은 초대 코드")
        invitation.deactivate()

        val inviter = memberJpaRepository.findByIdOrNull(invitation.inviterId) ?: throw InvalidRequestException("초대 보낸 사용자를 찾을 수 없음")
        val invitee = memberJpaRepository.findByIdOrNull(inviteeId) ?: throw InvalidRequestException("존재하지 않는 사용자 id : $inviteeId")
        val memberRelationEntity = MemberRelationEntity(
            inviterId = inviter.id!!,
            inviteeId = invitee.id!!,
            relationType = invitation.relationType
        )
        memberRelationJpaRepository.save(memberRelationEntity)

        invitee.memberType = MemberType.ofCode(invitationCode.substring(0, 2))
        when (invitee.memberType) {
            MemberType.CANDIDATE -> {
                invitee.memberType = MemberType.CANDIDATE
                matchCandidateJpaRepository.save(MatchCandidateEntity(member = invitee))
            }
            MemberType.MATCHMAKER -> {
                invitee.memberType = MemberType.MATCHMAKER
                matchMakerJpaRepository.save(MatchMakerEntity(member = invitee))
            }
            else -> {}
        }
        return invitee.memberType
    }

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }
}