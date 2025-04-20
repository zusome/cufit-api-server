package com.official.cufitapi.domain.notification.application.command

data class RegisterMessageCommand(
    val content: String,
    val type: String,
    val messages: List<RegisterMessageDetail>,
    val contentType: String,
    val countryCode: String,
    val subject: String?,
    val files: List<RegisterMessageFileCommand>,
    val messageReserveTime: String?,
    val messageReserveTimeUnit: String?
) {
}

data class RegisterMessageDetail(
    val to: String,
    val content: String,
    val subject: String? = null,
    val id: Long? = null
)

data class RegisterMessageFileCommand(
    val fileId: String,
)
