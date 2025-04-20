package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.match.domain.event.SucceedMatchEvent
import com.official.cufitapi.domain.match.domain.event.RejectedMatchEvent
import com.official.cufitapi.domain.match.domain.event.SuggestedMatchEvent
import com.official.cufitapi.domain.notification.api.event.acl.RegisterNotificationCommandsAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.acl.RegisterNotificationCommandAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.temp.PushNotificationFacadeService
import com.official.cufitapi.domain.notification.application.RegisterNotificationUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MatchEventHandler(
    private val registerNotificationCommandsAntiCorruptionLayer: RegisterNotificationCommandsAntiCorruptionLayer,
    private val registerNotificationCommandAntiCorruptionLayer: RegisterNotificationCommandAntiCorruptionLayer,
    private val registerNotificationUseCase: RegisterNotificationUseCase,
    private val pushNotificationFacadeService: PushNotificationFacadeService,
) {

    @EventListener
    fun handleSucceedMatchEvent(event: SucceedMatchEvent) {
        val commands = registerNotificationCommandsAntiCorruptionLayer.commands(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        commands.forEach { registerNotificationUseCase.register(it) }
    }

    @EventListener
    fun handleRejectedMatchEvent(event: RejectedMatchEvent) {
        val commands = registerNotificationCommandsAntiCorruptionLayer.commands(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        commands.forEach { registerNotificationUseCase.register(it) }
    }

    @EventListener
    fun handleSuggestedMatchEvent(event: SuggestedMatchEvent) {
        val commands = registerNotificationCommandsAntiCorruptionLayer.commands(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        commands.forEach { registerNotificationUseCase.register(it) }
    }
}
