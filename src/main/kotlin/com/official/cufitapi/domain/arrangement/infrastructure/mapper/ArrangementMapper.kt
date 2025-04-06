package com.official.cufitapi.domain.arrangement.infrastructure.mapper

import com.official.cufitapi.domain.arrangement.domain.Arrangement
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementEntity
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementStatus
import org.springframework.stereotype.Component

@Component
class ArrangementMapper {

    fun mapToDomain(arrangementEntity: ArrangementEntity): Arrangement {
        return Arrangement(
            makerMemberId = arrangementEntity.makerMemberId,
            leftCandidateId = arrangementEntity.leftCandidateMemberId,
            rightCandidateId = arrangementEntity.rightCandidateId,
            arrangementStatus = ArrangementStatus.of(arrangementEntity.arrangementStatus),
            id = arrangementEntity.id,
        )
    }

    fun mapToEntity(arrangement: Arrangement): ArrangementEntity {
        return ArrangementEntity(
            makerMemberId = arrangement.makerMemberId,
            leftCandidateMemberId = arrangement.leftCandidateId,
            rightCandidateId = arrangement.rightCandidateId,
            arrangementStatus = arrangement.arrangementStatus.step,
            id = arrangement.id,
        )
    }
}
