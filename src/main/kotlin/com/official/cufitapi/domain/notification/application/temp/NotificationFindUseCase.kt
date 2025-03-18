package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.domain.Notification

@FunctionalInterface
interface NotificationFindUseCase {
    fun findAll(memberId : Long) : List<Notification>
}
