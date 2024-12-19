package com.official.cufitapi.domain.application

import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeResponse
import com.official.cufitapi.domain.api.dto.invitation.InvitationResponse
import com.official.cufitapi.domain.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.enums.MemberType
import com.official.cufitapi.domain.infrastructure.entity.Invitation
import com.official.cufitapi.domain.infrastructure.repository.InvitationJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class InvitationService(
    private val invitationJpaRepository: InvitationJpaRepository,
    private val memberJpaRepository: MemberJpaRepository
) {

    companion object {
        private const val BASE_62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    }

    @Transactional
    fun validate(memberId: Long, request: InvitationCodeRequest): InvitationResponse {
        val member = memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("존재하지 않는 사용자 id : $memberId")
        if (MemberType.invitationCodePrefix(member.currentType) != request.invitationCode.substring(0,2)) {
            throw InvalidRequestException("잘못된 사용자 초대코드")
        }

        if (!invitationJpaRepository.existsByMemberIdAndCodeAndIsActivatedIsTrue(memberId, request.invitationCode)) {
            throw InvalidRequestException("잘못된 사용자 초대코드")
        }

        // 검증 성공하면, 초대코드 Soft Delete
        val invitation = invitationJpaRepository.findByCode(request.invitationCode) ?: throw InvalidRequestException("유효하지 않은 초대 코드")
        invitation.deactivate()

        val invitee = memberJpaRepository.findByIdOrNull(invitation.senderId) ?: throw InvalidRequestException("초대 보낸 사용자를 찾을 수 없음")
        return InvitationResponse(
            inviteeName = invitee.name
        )
    }

    @Transactional
    fun generateInvitationCode(memberId: Long, request: InvitationCodeGenerateRequest) : InvitationCodeResponse {
        val sender = memberJpaRepository.findByIdOrNull(memberId) ?: throw InvalidRequestException("잘못된 사용자 id 요청 : $memberId")
        val invitationCodePrefix = MemberType.invitationCodePrefix(request.memberType)
        val invitationCodeSuffix = MatchMakerCandidateRelationType.invitationCodeSuffix(request.relationType)
        val invitationCode =  invitationCodePrefix + generateRandomBase62String() + invitationCodeSuffix
        val invitation = invitationJpaRepository.save(
            Invitation(
            code = invitationCode,
            relationType = request.relationType,
            senderId = sender.id!!
        ))
        return InvitationCodeResponse(
            invitationCode = invitation.code
        )
    }

    private fun generateRandomBase62String(length: Int = 8): String {
        return (1..length)
            .map { Random.nextInt(0, BASE_62_CHARS.length) }
            .map(BASE_62_CHARS::get)
            .joinToString("")
    }
}