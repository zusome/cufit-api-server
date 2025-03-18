package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.event.RegisteredInvitationCardEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.PushNotificationFacadeService
import com.official.cufitapi.domain.notification.application.RegisterInAppNotificationUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationInvitationCardEventHandler(
    private val inAppNotificationAntiCorruptionLayer: InAppNotificationAntiCorruptionLayer,
    private val registerInAppNotificationUseCase: RegisterInAppNotificationUseCase,
    private val pushNotificationFacadeService: PushNotificationFacadeService,
) {

    @EventListener
    fun handleInvitationCardAcceptEvent(event: RegisteredInvitationCardEvent) {
        val command = inAppNotificationAntiCorruptionLayer.command(InAppNotificationAntiCorruptionLayerDto(event))
        registerInAppNotificationUseCase.register(command)
    }

    @EventListener
    fun handleInvitationCardAcceptEvent(event: AcceptedInvitationCardEvent) {
        val command = inAppNotificationAntiCorruptionLayer.command(InAppNotificationAntiCorruptionLayerDto(event))
        registerInAppNotificationUseCase.register(command)
    }
}
