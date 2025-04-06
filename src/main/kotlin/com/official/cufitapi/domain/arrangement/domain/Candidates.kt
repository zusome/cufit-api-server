package com.official.cufitapi.domain.arrangement.domain

interface Candidates {
    fun isSameGender(leftCandidateId: Long, rightCandidateId: Long): Boolean
}
