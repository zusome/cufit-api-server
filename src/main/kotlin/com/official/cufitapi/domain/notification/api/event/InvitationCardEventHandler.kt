package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.event.RegisteredInvitationCardEvent
import com.official.cufitapi.domain.notification.api.event.acl.RegisterNotificationCommandAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.temp.PushNotificationFacadeService
import com.official.cufitapi.domain.notification.application.RegisterNotificationUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class InvitationCardEventHandler(
    private val registerNotificationCommandAntiCorruptionLayer: RegisterNotificationCommandAntiCorruptionLayer,
    private val registerNotificationUseCase: RegisterNotificationUseCase,
    private val pushNotificationFacadeService: PushNotificationFacadeService,
) {

    @EventListener
    fun handleInvitationCardAcceptEvent(event: RegisteredInvitationCardEvent) {
        val command = registerNotificationCommandAntiCorruptionLayer.command(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        registerNotificationUseCase.register(command)
    }

    @EventListener
    fun handleInvitationCardAcceptEvent(event: AcceptedInvitationCardEvent) {
        val command = registerNotificationCommandAntiCorruptionLayer.command(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        registerNotificationUseCase.register(command)
    }
}
