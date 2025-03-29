package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.arrangement.domain.event.MatchedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.RejectedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.SuggestedArrangementEvent
import com.official.cufitapi.domain.notification.api.event.acl.BroadcastInAppNotificationAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.PushNotificationFacadeService
import com.official.cufitapi.domain.notification.application.RegisterInAppNotificationUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationArrangementEventHandler(
    private val broadcastInAppNotificationAntiCorruptionLayer: BroadcastInAppNotificationAntiCorruptionLayer,
    private val inAppNotificationAntiCorruptionLayer: InAppNotificationAntiCorruptionLayer,
    private val registerInAppNotificationUseCase: RegisterInAppNotificationUseCase,
    private val pushNotificationFacadeService: PushNotificationFacadeService,
) {

    @EventListener
    fun handleMatchedArrangementEvent(event: MatchedArrangementEvent) {
        val commands = broadcastInAppNotificationAntiCorruptionLayer.commands(InAppNotificationAntiCorruptionLayerDto(event))
        commands.forEach { registerInAppNotificationUseCase.register(it) }
    }

    @EventListener
    fun handleRejectedArrangementEvent(event: RejectedArrangementEvent) {
        val commands = broadcastInAppNotificationAntiCorruptionLayer.commands(InAppNotificationAntiCorruptionLayerDto(event))
        commands.forEach { registerInAppNotificationUseCase.register(it) }
    }

    @EventListener
    fun handleSuggestedArrangementEvent(event: SuggestedArrangementEvent) {
        val commands = broadcastInAppNotificationAntiCorruptionLayer.commands(InAppNotificationAntiCorruptionLayerDto(event))
        commands.forEach { registerInAppNotificationUseCase.register(it) }
    }
}
