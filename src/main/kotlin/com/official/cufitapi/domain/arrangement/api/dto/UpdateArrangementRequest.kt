package com.official.cufitapi.domain.arrangement.api.dto

import com.official.cufitapi.domain.arrangement.application.command.UpdateArrangementCommand

data class UpdateArrangementRequest(
    val isAccepted: Boolean
) {
    fun toCommand(arrangementId: Long): UpdateArrangementCommand =
        UpdateArrangementCommand(arrangementId, isAccepted)
}
