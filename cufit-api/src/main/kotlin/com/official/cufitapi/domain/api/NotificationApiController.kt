package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.dto.notification.NotificationResponse
import com.official.cufitapi.domain.application.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@ApiV1Controller
class NotificationApiController(
    private val notificationService: NotificationService
) {

    @GetMapping
    fun getNotificationList(
        memberId: Long
    ) : ResponseEntity<List<NotificationResponse>> {
        return ResponseEntity.ok(notificationService.findAll(memberId))
    }
}