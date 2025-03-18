package com.official.cufitapi.domain.notification.domain

interface InAppNotificationRepository {
    fun save(inAppNotification: InAppNotification): InAppNotification
}
