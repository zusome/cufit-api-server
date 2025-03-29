package com.official.cufitapi.domain.arrangement.domain.event

data class MatchedArrangementEvent(
    val id: Long,
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val arrangementStatus: Int
)
