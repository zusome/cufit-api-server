package com.official.cufitapi.domain.match.api.dto

import com.official.cufitapi.domain.match.application.command.SuggestMatchCommand

data class SuggestMatchRequest(
    val leftCandidateId: Long,
    val rightCandidateId: Long,
) {
    fun toCommand(makerMemberId: Long): SuggestMatchCommand {
        return SuggestMatchCommand(
            makerMemberId = makerMemberId,
            leftCandidateId = leftCandidateId,
            rightCandidateId = rightCandidateId
        )
    }
}
