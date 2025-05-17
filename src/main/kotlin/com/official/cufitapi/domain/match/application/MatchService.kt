package com.official.cufitapi.domain.match.application

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.match.application.command.SuggestMatchCommand
import com.official.cufitapi.domain.match.application.command.UpdateMatchCommand
import com.official.cufitapi.domain.match.domain.Match
import com.official.cufitapi.domain.match.domain.MatchRepository
import com.official.cufitapi.domain.match.domain.Candidates
import com.official.cufitapi.domain.match.domain.MemberRelations
import com.official.cufitapi.domain.match.domain.event.SucceedMatchEvent
import com.official.cufitapi.domain.match.domain.event.RejectedMatchEvent
import com.official.cufitapi.domain.match.domain.event.SuggestedMatchEvent
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchStatus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Objects

fun interface SuggestMatchUsecase {
    fun suggest(command: SuggestMatchCommand): Long
}

fun interface UpdateMatchUsecase {
    fun agreeOrReject(command: UpdateMatchCommand)
}

@Service
class MatchService(
    private val candidates: Candidates,
    private val memberRelations: MemberRelations,
    private val matchRepository: MatchRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : SuggestMatchUsecase, UpdateMatchUsecase {

    /**
     * 주선자 <-> 내후보자 == 하루에 3번 가능
     * 주선자 <-> 상대후보자 == 같은 사람 불가능
     * 나중에 내후보자끼리도 주선 가능 == 동일 로직
     */
    override fun suggest(command: SuggestMatchCommand): Long {
        val today = DateTimeUtils.beginToday()
        val tomorrow = today.tomorrow()

        verifyCandidate(command.makerMemberId, command.leftCandidateId, today, tomorrow)
        verifyCandidate(command.makerMemberId, command.rightCandidateId, today, tomorrow)
        verifySameGender(command.leftCandidateId, command.rightCandidateId)
        verifyExists(command.makerMemberId, command.leftCandidateId, command.rightCandidateId)

        val match = Match(
            makerMemberId = command.makerMemberId,
            leftCandidateId = command.leftCandidateId,
            leftCandidateAgree = false,
            rightCandidateId = command.rightCandidateId,
            rightCandidateAgree = false,
            matchStatus = MatchStatus.SUGGESTED
        )
        val matchId = matchRepository.save(match).let { it.id!! }
        applicationEventPublisher.publishEvent(
            SuggestedMatchEvent(
                matchId,
                command.makerMemberId,
                command.leftCandidateId,
                command.rightCandidateId,
                match.matchStatus.step
            )
        )
        return matchId
    }

    private fun verifyExists(makerMemberId: Long, leftCandidateId: Long, rightCandidateId: Long) {
        val matches = matchRepository.findByCandidates(makerMemberId, leftCandidateId, rightCandidateId)
        if (Objects.nonNull(matches)) {
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
            val matches = matchRepository.findAllByPeriod(makerMemberId, candidateId, today, tomorrow)
            if (matches.size >= 3) {
                throw InvalidRequestException(ErrorCode.DAILY_MAXIMUM_MATCHING_COUNT)
            }
        }
    }

    override fun agreeOrReject(command: UpdateMatchCommand) {
        val match = matchRepository.findById(command.matchId)
        match.agreeOrReject(command.memberId, command.isAccepted)
        val updatedMatch = matchRepository.save(match)
        if (updatedMatch.isMatched()) {
            applicationEventPublisher.publishEvent(
                SucceedMatchEvent(
                    match.id!!,
                    match.makerMemberId,
                    match.leftCandidateId,
                    match.rightCandidateId,
                    match.matchStatus.step
                )
            )
        }
        if (match.isRejected()) {
            applicationEventPublisher.publishEvent(
                RejectedMatchEvent(
                    match.id!!,
                    match.makerMemberId,
                    match.leftCandidateId,
                    match.rightCandidateId,
                    match.matchStatus.step,
                    command.memberId
                )
            )
        }
    }
}
