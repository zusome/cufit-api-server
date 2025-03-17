package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.MatchCandidate

interface MatchCandidateRepository {
    fun findByMemberId(memberId: Long): MatchCandidate
    fun save(matchCandidate: MatchCandidate): MatchCandidate
}
