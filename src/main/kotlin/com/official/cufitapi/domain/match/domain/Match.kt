package com.official.cufitapi.domain.match.domain

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchStatus

class Match(
    val makerMemberId: Long,
    val leftCandidateId: Long,
    var leftCandidateAgree: Boolean,
    val rightCandidateId: Long,
    var rightCandidateAgree: Boolean,
    var matchStatus: MatchStatus,
    val id: Long? = null,
) {

    fun agreeOrReject(memberId: Long, isAccepted: Boolean) {
        if (leftCandidateId == memberId) {
            leftCandidateAgree = isAccepted
        }
        if (rightCandidateId == memberId) {
            rightCandidateAgree = isAccepted
        }
        if(memberId != leftCandidateId && memberId != rightCandidateId) {
            throw NotFoundException(ErrorCode.NOT_FOUND_CANDIDATE)
        }
        nextStatus(isAccepted)
    }

    private fun nextStatus(isAccepted: Boolean) {
        if (!isAccepted) {
            this.matchStatus = MatchStatus.REJECTED
            return
        }
        if (isAccepted && matchStatus.hasNext()) {
            this.matchStatus = matchStatus.nextStatus()
            return
        }
    }

    fun isMatched(): Boolean {
        return this.matchStatus == MatchStatus.MATCHED
    }

    fun isRejected(): Boolean {
        return this.matchStatus == MatchStatus.REJECTED
    }
}
