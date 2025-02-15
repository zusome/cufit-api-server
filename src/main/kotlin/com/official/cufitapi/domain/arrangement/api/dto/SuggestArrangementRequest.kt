package com.official.cufitapi.domain.arrangement.api.dto

import com.official.cufitapi.domain.arrangement.application.command.SuggestArrangementCommand

data class SuggestArrangementRequest(
    val leftCandidateId: Long,
    val rightCandidateId: Long,
) {
    fun toCommand(matchMakerMemberId: Long): SuggestArrangementCommand {
        return SuggestArrangementCommand(
            matchMakerMemberId = matchMakerMemberId,
            leftCandidateId = leftCandidateId,
            rightCandidateId = rightCandidateId
        )
    }
}
