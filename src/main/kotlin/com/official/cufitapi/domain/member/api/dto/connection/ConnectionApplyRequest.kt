package com.official.cufitapi.domain.member.api.dto.connection

data class ConnectionApplyRequest(
    val matchMakerId: Long,
    val receiverId: Long,
    val senderId: Long
) {
}