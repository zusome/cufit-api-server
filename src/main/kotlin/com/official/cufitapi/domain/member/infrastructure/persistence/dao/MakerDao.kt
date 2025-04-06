package com.official.cufitapi.domain.member.infrastructure.persistence.dao

import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidates

interface MakerDao {
    fun candidateCount(memberId: Long): Long
    fun otherCandidateCount(memberId: Long): Long
    fun findCandidates(memberId: Long): Candidates
    fun findOtherCandidates(memberId: Long): OtherCandidates
}
