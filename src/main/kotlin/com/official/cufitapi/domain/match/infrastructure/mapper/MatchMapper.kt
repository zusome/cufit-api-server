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
            leftCandidateAgree = jpaMatch.leftCandidateAgree,
            rightCandidateId = jpaMatch.rightCandidateId,
            rightCandidateAgree = jpaMatch.rightCandidateAgree,
            matchStatus = MatchStatus.of(jpaMatch.status),
            id = jpaMatch.id,
        )
    }

    fun mapToEntity(match: Match): JpaMatch {
        return JpaMatch(
            makerMemberId = match.makerMemberId,
            leftCandidateMemberId = match.leftCandidateId,
            leftCandidateAgree = match.leftCandidateAgree,
            rightCandidateId = match.rightCandidateId,
            rightCandidateAgree = match.rightCandidateAgree,
            status = match.matchStatus.step,
            id = match.id,
        )
    }
}
