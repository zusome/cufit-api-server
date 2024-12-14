package com.official.cufitapi.domain.api.dto.connection

data class ConnectionApplyRequest(
    val matchMakerId: Long,
    val receiverId: Long,
    val senderId: Long
) {
}