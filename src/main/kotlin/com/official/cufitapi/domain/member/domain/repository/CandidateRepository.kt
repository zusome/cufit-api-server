package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.Candidate

interface CandidateRepository {
    fun findByMemberId(memberId: Long): Candidate
    fun save(candidate: Candidate): Candidate
}
