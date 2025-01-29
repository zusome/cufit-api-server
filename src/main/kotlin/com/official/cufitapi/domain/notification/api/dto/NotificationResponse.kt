package com.official.cufitapi.domain.notification.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.official.cufitapi.domain.notification.appliaction.NotificationType
import com.official.cufitapi.domain.notification.domain.Notification
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(name = "알림 응답")
data class NotificationResponse(
    @Schema(description = "알림 ID", example = "10")
    val id: Long,
    @Schema(description = "알림 제목", example = "박유나님 프로필 등록 완료")
    val title: String,
    @Schema(description = "알림 내용", example = "박유나님이 프로필을 완성했어요.")
    val content: String,
    @Schema(description = "알림 타입", example = "")
    val notificationType: NotificationType,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdDate: LocalDateTime
) {
    fun of(notification: Notification): NotificationResponse {
        return NotificationResponse(
            id = id,
            title = title,
            content = content,
            notificationType = notificationType,
            createdDate = createdDate
        )
    }
}
