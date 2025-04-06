package com.official.cufitapi.domain.arrangement.domain.event

data class RejectedArrangementEvent(
    val id: Long,
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val arrangementStatus: Int,
    val rejectedBy: Long
)
