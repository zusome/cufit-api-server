package com.official.cufitapi.domain.match.api.dto

import com.official.cufitapi.domain.match.application.command.UpdateMatchCommand

data class UpdateMatchRequest(
    val isAccepted: Boolean
) {
    fun toCommand(matchId: Long, memberId: Long): UpdateMatchCommand =
        UpdateMatchCommand(matchId, isAccepted, memberId)
}
