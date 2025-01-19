package com.official.cufitapi.domain.arrangement.infrastructure.dto

data class ArrangementInfo(
    val arrangementId: Long,
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val status: String,
) {
}