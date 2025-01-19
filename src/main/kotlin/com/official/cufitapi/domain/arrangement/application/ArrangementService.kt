package com.official.cufitapi.domain.arrangement.application

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.exception.InvalidRequestException
import com.official.cufitapi.common.tomorrow
import com.official.cufitapi.domain.arrangement.application.command.SuggestArrangementCommand
import com.official.cufitapi.domain.arrangement.application.command.UpdateArrangementCommand
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementEntity
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementRepository
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
        verifyCandidate(command.matchMakerId, command.leftCandidateId, today, tomorrow)
        verifyCandidate(command.matchMakerId, command.rightCandidateId, today, tomorrow)
        verifySameGender(command.leftCandidateId, command.rightCandidateId)
        verifyExists(command.matchMakerId, command.leftCandidateId, command.rightCandidateId)
        val arrangementEntity = ArrangementEntity(
            matchMakerId = command.matchMakerId,
            leftCandidateId = command.leftCandidateId,
            rightCandidateId = command.rightCandidateId,
            arrangementStatus = ArrangementStatus.SUGGESTED
        )
        return arrangementRepository.save(arrangementEntity).let { it.id!! }
    }

    private fun verifyExists(matchMakerId: Long, leftCandidateId: Long, rightCandidateId: Long) {
        if(arrangementRepository.existsCandidates(matchMakerId, leftCandidateId, rightCandidateId)) {
            throw InvalidRequestException("이미 주선된 후보자입니다.")
        }
    }

    private fun verifySameGender(leftCandidateId: Long, rightCandidateId: Long) {
        val leftCandidate = matchCandidateJpaRepository.findByMemberId(leftCandidateId)
            ?: throw InvalidRequestException("존재하지 않는 Receiver")
        val rightCandidate = matchCandidateJpaRepository.findByMemberId(rightCandidateId)
            ?: throw InvalidRequestException("존재하지 않는 Sender")
        if (leftCandidate.isSameGender(rightCandidate)) {
            throw InvalidRequestException("같은 성별에게는 연결 요청을 보낼 수 없습니다.")
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
            val count = arrangementRepository.todayCount(matchMakerId, candidateId, today, tomorrow)
            if (count >= 3) {
                throw RuntimeException("하루에 3회 이상 매칭을 할 수 없습니다.")
            }
        }
    }

    override fun updateArrangement(command: UpdateArrangementCommand) {
        val arrangementEntity = arrangementRepository.findByIdOrNull(command.arrangementId)
            ?: throw InvalidRequestException("존재하지 않는 주선")
        arrangementEntity.nextStatus(command.isAccepted)
        arrangementRepository.save(arrangementEntity)
    }
}
