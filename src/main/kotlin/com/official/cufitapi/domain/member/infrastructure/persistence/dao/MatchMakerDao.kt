package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidates

interface MatchMakerDao {
    fun candidateCount(memberId: Long): Long
    fun otherCandidateCount(memberId: Long): Long
    fun findCandidates(memberId: Long): MatchCandidates
    fun findOtherCandidates(memberId: Long): OtherMatchCandidates
}
