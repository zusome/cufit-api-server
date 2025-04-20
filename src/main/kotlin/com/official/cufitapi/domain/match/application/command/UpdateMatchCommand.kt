package com.official.cufitapi.domain.match.application.command

data class UpdateMatchCommand(
    val matchId: Long,
    val isAccepted: Boolean,
    val memberId: Long
) {
}
