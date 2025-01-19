package com.official.cufitapi.domain.arrangement.application.command

data class UpdateArrangementCommand(
    val arrangementId: Long,
    val isAccepted: Boolean
) {
}