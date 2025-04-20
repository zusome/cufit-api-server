package com.official.cufitapi.domain.match.infrastructure.dto

data class MatchInfo(
    val matchId: Long,
    val makerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long,
    val status: String,
) {
}
