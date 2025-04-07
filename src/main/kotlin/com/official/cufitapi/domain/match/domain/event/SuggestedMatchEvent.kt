package com.official.cufitapi.domain.match.domain.event

data class SuggestedMatchEvent(
    val id: Long,
    val makerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val matchStatus: Int,
)
