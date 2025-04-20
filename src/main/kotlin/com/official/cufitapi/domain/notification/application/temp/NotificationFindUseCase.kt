package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.domain.temp.NotificationLegacy

@FunctionalInterface
interface NotificationFindUseCase {
    fun findAll(memberId : Long) : List<NotificationLegacy>
}
