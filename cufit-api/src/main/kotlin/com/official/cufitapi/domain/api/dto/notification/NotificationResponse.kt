package com.official.cufitapi.domain.api.dto.notification

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class NotificationResponse(
    val id: Long,
    val title: String,
    val content: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern="yyyy-MM-dd'T'HH:mm:ss")
    val createdDate: LocalDateTime
)
