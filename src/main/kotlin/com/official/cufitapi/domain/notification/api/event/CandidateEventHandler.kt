package com.official.cufitapi.domain.notification.api.event

import com.official.cufitapi.domain.member.domain.event.UpdatedCandidateProfileEvent
import com.official.cufitapi.domain.notification.api.event.acl.RegisterNotificationCommandAntiCorruptionLayer
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.temp.PushNotificationFacadeService
import com.official.cufitapi.domain.notification.application.RegisterNotificationUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CandidateEventHandler(
    private val registerNotificationCommandAntiCorruptionLayer: RegisterNotificationCommandAntiCorruptionLayer,
    private val registerNotificationUseCase: RegisterNotificationUseCase,
    private val pushNotificationFacadeService: PushNotificationFacadeService,
) {

    @EventListener
    fun handleUpdateCandidateProfileEvent(event: UpdatedCandidateProfileEvent) {
        val command = registerNotificationCommandAntiCorruptionLayer.command(
            RegisterNotificationCommandAntiCorruptionLayerDto(event)
        )
        registerNotificationUseCase.register(command)
    }
}
