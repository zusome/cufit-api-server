package com.official.cufitapi.domain.arrangement.application

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.arrangement.application.command.SuggestArrangementCommand
import com.official.cufitapi.domain.arrangement.application.command.UpdateArrangementCommand
import com.official.cufitapi.domain.arrangement.domain.Arrangement
import com.official.cufitapi.domain.arrangement.domain.ArrangementRepository
import com.official.cufitapi.domain.arrangement.domain.Candidates
import com.official.cufitapi.domain.arrangement.domain.MemberRelations
import com.official.cufitapi.domain.arrangement.domain.event.MatchedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.RejectedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.SuggestedArrangementEvent
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementStatus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Objects

fun interface SuggestArrangementUsecase {
    fun suggestArrangement(command: SuggestArrangementCommand): Long
}

fun interface UpdateArrangementUsecase {
    fun nextStep(command: UpdateArrangementCommand)
}

@Service
class ArrangementService(
    private val candidates: Candidates,
    private val memberRelations: MemberRelations,
    private val arrangementRepository: ArrangementRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : SuggestArrangementUsecase, UpdateArrangementUsecase {

    /**
     * 주선자 <-> 내후보자 == 하루에 3번 가능
     * 주선자 <-> 상대후보자 == 같은 사람 불가능
     * 나중에 내후보자끼리도 주선 가능 == 동일 로직
     */
    override fun suggestArrangement(command: SuggestArrangementCommand): Long {
        val today = DateTimeUtils.beginToday()
        val tomorrow = today.tomorrow()

        verifyCandidate(command.makerMemberId, command.leftCandidateId, today, tomorrow)
        verifyCandidate(command.makerMemberId, command.rightCandidateId, today, tomorrow)
        verifySameGender(command.leftCandidateId, command.rightCandidateId)
        verifyExists(command.makerMemberId, command.leftCandidateId, command.rightCandidateId)

        val arrangement = Arrangement(
            makerMemberId = command.makerMemberId,
            leftCandidateId = command.leftCandidateId,
            rightCandidateId = command.rightCandidateId,
            arrangementStatus = ArrangementStatus.SUGGESTED
        )
        val arrangementId = arrangementRepository.save(arrangement).let { it.id!! }
        applicationEventPublisher.publishEvent(
            SuggestedArrangementEvent(
                arrangementId,
                command.makerMemberId,
                command.leftCandidateId,
                command.rightCandidateId,
                arrangement.arrangementStatus.step
            )
        )
        return arrangementId
    }

    private fun verifyExists(makerMemberId: Long, leftCandidateId: Long, rightCandidateId: Long) {
        val arrangement = arrangementRepository.findByCandidates(makerMemberId, leftCandidateId, rightCandidateId)
        if (Objects.nonNull(arrangement)) {
            throw InvalidRequestException(ErrorCode.ALREADY_MATCHED_CANDIDATE)
        }
    }

    private fun verifySameGender(leftCandidateId: Long, rightCandidateId: Long) {
        if (candidates.isSameGender(leftCandidateId, rightCandidateId)) {
            throw InvalidRequestException(ErrorCode.CONNECTION_REQUEST_SAME_GENDER)
        }
    }

    private fun verifyCandidate(
        makerMemberId: Long,
        candidateId: Long,
        today: LocalDateTime,
        tomorrow: LocalDateTime,
    ) {
        val memberRelation = memberRelations.findByInviterIdAndInviteeId(makerMemberId, candidateId)
        if (Objects.nonNull(memberRelation)) {
            val arrangements = arrangementRepository.findAllByPeriod(makerMemberId, candidateId, today, tomorrow)
            if (arrangements.size >= 3) {
                throw InvalidRequestException(ErrorCode.DAILY_MAXIMUM_MATCHING_COUNT)
            }
        }
    }

    override fun nextStep(command: UpdateArrangementCommand) {
        val arrangement = arrangementRepository.findById(command.arrangementId)
        arrangement.nextStep(command.isAccepted)
        arrangementRepository.save(arrangement)
        if (arrangement.isMatched()) {
            applicationEventPublisher.publishEvent(
                MatchedArrangementEvent(
                    arrangement.id!!,
                    arrangement.makerMemberId,
                    arrangement.leftCandidateId,
                    arrangement.rightCandidateId,
                    arrangement.arrangementStatus.step
                )
            )
        }
        if (arrangement.isRejected()) {
            applicationEventPublisher.publishEvent(
                RejectedArrangementEvent(
                    arrangement.id!!,
                    arrangement.makerMemberId,
                    arrangement.leftCandidateId,
                    arrangement.rightCandidateId,
                    arrangement.arrangementStatus.step,
                    command.memberId
                )
            )
        }
    }
}
