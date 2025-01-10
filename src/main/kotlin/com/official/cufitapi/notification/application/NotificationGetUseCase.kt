package com.official.cufitapi.notification.application

import com.official.cufitapi.notification.domain.Notification

interface NotificationGetUseCase {
    fun findAll(memberId: Long) : List<Notification>
}