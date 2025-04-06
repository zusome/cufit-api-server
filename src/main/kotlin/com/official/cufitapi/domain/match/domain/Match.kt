package com.official.cufitapi.domain.match.domain

import com.official.cufitapi.domain.match.infrastructure.persistence.JpaMatch
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchStatus

class Match(
    val makerMemberId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    var matchStatus: MatchStatus,
    val id: Long? = null,
) {

    fun nextStep(isAccepted: Boolean) {
        if (isAccepted && matchStatus.hasNext()) {
            this.matchStatus = matchStatus.nextStatus()
            return
        }
        if(matchStatus.isCancelled()) {
            throw IllegalStateException("Already cancelled")
        }
        this.matchStatus = MatchStatus.REJECTED
    }

    fun isMatched(): Boolean {
        return this.matchStatus == MatchStatus.MATCHED
    }

    fun isRejected(): Boolean {
        return this.matchStatus == MatchStatus.REJECTED
    }

    companion object {
        fun fromEntity(entity: JpaMatch): Match {
            return Match(
                id = entity.id,
                makerMemberId = entity.makerMemberId,
                leftCandidateId = entity.leftCandidateMemberId,
                rightCandidateId = entity.rightCandidateId,
                matchStatus = MatchStatus.of(entity.status)
            )
        }
    }
}
