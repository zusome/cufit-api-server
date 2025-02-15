package com.official.cufitapi.domain.arrangement.application.command

data class SuggestArrangementCommand(
    val matchMakerMemberId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long
) {
}
