package com.official.cufitapi.domain.member.infrastructure.persistence.dao

interface MatchMaker {
    fun candidateCount(memberId: Long): Long
    fun otherCandidateCount(memberId: Long): Long
}
