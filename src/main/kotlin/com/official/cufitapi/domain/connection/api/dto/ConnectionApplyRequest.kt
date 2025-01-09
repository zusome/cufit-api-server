package com.official.cufitapi.domain.connection.api.dto

data class ConnectionApplyRequest(
    val matchMakerId: Long,
    val receiverId: Long,
    val senderId: Long
) {
}