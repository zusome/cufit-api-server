package com.official.cufitapi.domain.connection.application.command

data class ConnectionUpdateCommand(
    val connectionId: Long,
    val matchStatus: String
) {
}