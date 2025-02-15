package com.official.cufitapi.domain.notification.domain.event

import com.official.cufitapi.domain.notification.domain.*
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val notificationRepository: NotificationRepository,
    private val pushAlarmClient: PushAlarmClient,
    private val deviceTokenRepository: DeviceTokenRepository
) {

    @EventListener
    fun handleNotificationSavedEvent(event: NotificationSavedEvent) {
        val deviceToken = deviceTokenRepository.findByMemberIdAndPlatform(event.notification.memberId, "FCM")
        pushAlarmClient.sendPushAlarm(
            PushAlarm(
                deviceToken.deviceToken,
                event.notification.title,
                event.notification.content,
            )
        )
        notificationRepository.save(event.notification)
    }
}