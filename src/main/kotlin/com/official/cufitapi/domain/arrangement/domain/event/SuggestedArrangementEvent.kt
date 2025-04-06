package com.official.cufitapi.domain.arrangement.domain.event

data class SuggestedArrangementEvent(
    val id: Long,
    val makerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val arrangementStatus: Int,
)
