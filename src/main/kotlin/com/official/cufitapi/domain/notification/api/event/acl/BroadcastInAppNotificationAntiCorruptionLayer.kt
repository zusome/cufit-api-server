package com.official.cufitapi.domain.notification.api.event.acl

import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import org.springframework.stereotype.Component

@Component
class BroadcastInAppNotificationAntiCorruptionLayer(
    private val inAppNotificationCommandACL: List<InAppNotificationAntiCorruptionMapper>,
) {

    fun <T> commands(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): List<RegisterInAppNotificationCommand> {
        return inAppNotificationCommandACL.filter { it.isSupportFeature(inAppNotificationAntiCorruptionLayerDto) }
            .map { it.registerCommand(inAppNotificationAntiCorruptionLayerDto) }
    }
}
