package com.official.cufitapi.domain.arrangement.domain

import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementEntity
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementStatus

class Arrangement(
    val id: Long?,
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    var arrangementStatus: ArrangementStatus
) {

    fun nextStatus(isAccepted: Boolean) {
        if (isAccepted) {
            this.arrangementStatus = arrangementStatus.nextStatus()
            return
        }
        this.arrangementStatus = ArrangementStatus.REJECTED
    }

    companion object {
        fun fromEntity(entity: ArrangementEntity): Arrangement {
            return Arrangement(
                id = entity.id,
                matchMakerId = entity.matchMakerId,
                leftCandidateId = entity.leftCandidateId,
                rightCandidateId = entity.rightCandidateId,
                arrangementStatus = entity.arrangementStatus
            )
        }
    }
}