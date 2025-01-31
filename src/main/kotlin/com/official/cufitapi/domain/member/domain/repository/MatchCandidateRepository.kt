package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.MatchCandidate

interface MatchCandidateRepository {
    fun findByMemberId(memberId: Long): MatchCandidate
    fun matchBreak(matchCandidate: MatchCandidate, isMatchAgreed: Boolean)
    fun updateProfile(matchCandidate: MatchCandidate)
}
