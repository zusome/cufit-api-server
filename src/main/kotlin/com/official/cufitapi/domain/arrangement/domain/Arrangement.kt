package com.official.cufitapi.domain.arrangement.domain

import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementEntity
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementStatus

class Arrangement(
    val makerMemberId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    var arrangementStatus: ArrangementStatus,
    val id: Long? = null,
) {

    fun nextStep(isAccepted: Boolean) {
        if (isAccepted && arrangementStatus.hasNext()) {
            this.arrangementStatus = arrangementStatus.nextStatus()
            return
        }
        if(arrangementStatus.isCancelled()) {
            throw IllegalStateException("Already cancelled")
        }
        this.arrangementStatus = ArrangementStatus.REJECTED
    }

    fun isMatched(): Boolean {
        return this.arrangementStatus == ArrangementStatus.MATCHED
    }

    fun isRejected(): Boolean {
        return this.arrangementStatus == ArrangementStatus.REJECTED
    }

    companion object {
        fun fromEntity(entity: ArrangementEntity): Arrangement {
            return Arrangement(
                id = entity.id,
                makerMemberId = entity.makerMemberId,
                leftCandidateId = entity.leftCandidateMemberId,
                rightCandidateId = entity.rightCandidateId,
                arrangementStatus = ArrangementStatus.of(entity.arrangementStatus)
            )
        }
    }
}
