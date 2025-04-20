package com.official.cufitapi.domain.match.infrastructure.mapper

import com.official.cufitapi.domain.match.domain.Match
import com.official.cufitapi.domain.match.infrastructure.persistence.JpaMatch
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchStatus
import org.springframework.stereotype.Component

@Component
class MatchMapper {

    fun mapToDomain(jpaMatch: JpaMatch): Match {
        return Match(
            makerMemberId = jpaMatch.makerMemberId,
            leftCandidateId = jpaMatch.leftCandidateMemberId,
            rightCandidateId = jpaMatch.rightCandidateId,
            matchStatus = MatchStatus.of(jpaMatch.status),
            id = jpaMatch.id,
        )
    }

    fun mapToEntity(match: Match): JpaMatch {
        return JpaMatch(
            makerMemberId = match.makerMemberId,
            leftCandidateMemberId = match.leftCandidateId,
            rightCandidateId = match.rightCandidateId,
            status = match.matchStatus.step,
            id = match.id,
        )
    }
}
