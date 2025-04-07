package com.official.cufitapi.domain.match.domain

interface Candidates {
    fun isSameGender(leftCandidateId: Long, rightCandidateId: Long): Boolean
}
