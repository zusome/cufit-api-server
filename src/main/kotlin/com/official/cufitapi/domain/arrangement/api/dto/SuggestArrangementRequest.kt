package com.official.cufitapi.domain.arrangement.api.dto

import com.official.cufitapi.domain.arrangement.application.command.SuggestArrangementCommand

data class SuggestArrangementRequest(
    val matchMakerId: Long,
    val leftCandidateId: Long,
    val rightCandidateId: Long
) {
    fun toCommand(): SuggestArrangementCommand {
        return SuggestArrangementCommand(
            matchMakerId = matchMakerId,
            leftCandidateId = leftCandidateId,
            rightCandidateId = rightCandidateId
        )
    }
}