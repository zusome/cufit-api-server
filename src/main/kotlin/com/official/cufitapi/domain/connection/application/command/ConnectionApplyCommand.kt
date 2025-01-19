package com.official.cufitapi.domain.connection.application.command

data class ConnectionApplyCommand(
    val memberId: Long,
    val matchMakerId: Long,
    val receiverId: Long,
    val senderId: Long
) {
}