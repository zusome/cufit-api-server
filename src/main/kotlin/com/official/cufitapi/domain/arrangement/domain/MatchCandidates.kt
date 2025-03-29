package com.official.cufitapi.domain.arrangement.domain

interface MatchCandidates {
    fun isSameGender(leftCandidateId: Long, rightCandidateId: Long): Boolean
}
