package com.official.cufitapi.domain.arrangement.infrastructure.dto

data class ArrangementInfo(
    val arrangementId: Long,
    val makerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val status: String,
) {
}
