package com.official.cufitapi.domain.arrangement.application

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.arrangement.application.command.SuggestArrangementCommand
import com.official.cufitapi.domain.arrangement.application.command.UpdateArrangementCommand
import com.official.cufitapi.domain.arrangement.domain.ArrangementRepository
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementEntity
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementJpaRepository
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementStatus
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberRelationJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

fun interface SuggestArrangementUsecase {
    fun suggestArrangement(command: SuggestArrangementCommand): Long
}

fun interface UpdateArrangementUsecase {
    fun updateArrangement(command: UpdateArrangementCommand)
}

@Service
class ArrangementService(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository,
    private val memberRelationJpaRepository: MemberRelationJpaRepository,
    private val arrangementJpaRepository: ArrangementJpaRepository,
    private val arrangementRepository: ArrangementRepository
): SuggestArrangementUsecase, UpdateArrangementUsecase {

    /**
     * 주선자 <-> 내후보자 == 하루에 3번 가능
     * 주선자 <-> 상대후보자 == 같은 사람 불가능
     * 나중에 내후보자끼리도 주선 가능 == 동일 로직
     */
    override fun suggestArrangement(command: SuggestArrangementCommand): Long {
        val today = DateTimeUtils.beginToday()
        val tomorrow = today.tomorrow()
        verifyCandidate(command.matchMakerMemberId, command.leftCandidateId, today, tomorrow)
        verifyCandidate(command.matchMakerMemberId, command.rightCandidateId, today, tomorrow)
        verifySameGender(command.leftCandidateId, command.rightCandidateId)
        verifyExists(command.matchMakerMemberId, command.leftCandidateId, command.rightCandidateId)
        val arrangementEntity = ArrangementEntity(
            matchMakerMemberId = command.matchMakerMemberId,
            leftCandidateMemberId = command.leftCandidateId,
            rightCandidateId = command.rightCandidateId,
            arrangementStatus = ArrangementStatus.SUGGESTED
        )
        return arrangementJpaRepository.save(arrangementEntity).let { it.id!! }
    }

    private fun verifyExists(matchMakerId: Long, leftCandidateId: Long, rightCandidateId: Long) {
        if(arrangementJpaRepository.existsCandidates(matchMakerId, leftCandidateId, rightCandidateId)) {
            throw InvalidRequestException(ErrorCode.ALREADY_MATCHED_CANDIDATE)
        }
    }

    private fun verifySameGender(leftCandidateId: Long, rightCandidateId: Long) {
        val leftCandidate = matchCandidateJpaRepository.findByMemberId(leftCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_RECEIVER)
        val rightCandidate = matchCandidateJpaRepository.findByMemberId(rightCandidateId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_SENDER)
        if (leftCandidate.isSameGender(rightCandidate)) {
            throw InvalidRequestException(ErrorCode.CONNECTION_REQUEST_SAME_GENDER)
        }
    }

    private fun verifyCandidate(
        matchMakerId: Long,
        candidateId: Long,
        today: LocalDateTime,
        tomorrow: LocalDateTime,
    ) {
        val isMyCandidate = memberRelationJpaRepository.existsByInviterIdAndInviteeId(matchMakerId, candidateId)
        if (isMyCandidate) {
            val count = arrangementJpaRepository.todayCount(matchMakerId, candidateId, today, tomorrow)
            if (count >= 3) {
                throw InvalidRequestException(ErrorCode.DAILY_MAXIMUM_MATCHING_COUNT)
            }
        }
    }

    override fun updateArrangement(command: UpdateArrangementCommand) {
        val arrangement = arrangementRepository.findById(command.arrangementId)
        arrangement.nextStatus(command.isAccepted)
        val arrangementEntity = arrangementJpaRepository.findByIdOrNull(command.arrangementId)
            ?: throw InvalidRequestException(ErrorCode.NOT_FOUND_ARRANGEMENT)
        arrangementEntity.nextStatus(command.isAccepted)
        arrangementJpaRepository.save(arrangementEntity)
    }
}
