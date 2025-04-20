package com.official.cufitapi.domain.notification.domain.temp.event

import com.official.cufitapi.domain.notification.domain.temp.DeviceTokenRepository
import com.official.cufitapi.domain.notification.domain.temp.NotificationLegacyRepository
import com.official.cufitapi.domain.notification.domain.temp.PushAlarm
import com.official.cufitapi.domain.notification.domain.temp.PushAlarmClient
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    // private val notificationLegacyRepository: NotificationLegacyRepository,
    private val pushAlarmClient: PushAlarmClient,
    private val deviceTokenRepository: DeviceTokenRepository
) {

    @EventListener
    fun handleNotificationSavedEvent(event: NotificationSavedEvent) {
        val deviceToken = deviceTokenRepository.findByMemberIdAndPlatform(event.notificationLegacy.memberId, "FCM")
        pushAlarmClient.sendPushAlarm(
            PushAlarm(
                deviceToken.deviceToken,
                event.notificationLegacy.title,
                event.notificationLegacy.content,
            )
        )
        // notificationLegacyRepository.save(event.notificationLegacy)
    }
}
