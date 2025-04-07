package com.official.cufitapi.domain.match.application.command

data class SuggestMatchCommand(
    val makerMemberId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long
) {
}
