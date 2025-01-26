package com.official.cufitapi.domain.notification.appliaction

import com.official.cufitapi.domain.notification.domain.Notification

@FunctionalInterface
interface NotificationFindUseCase {
    fun findAll(memberId : Long) : List<Notification>
}