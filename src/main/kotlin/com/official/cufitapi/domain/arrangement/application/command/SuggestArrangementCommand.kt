package com.official.cufitapi.domain.arrangement.application.command

data class SuggestArrangementCommand(
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long
) {
}