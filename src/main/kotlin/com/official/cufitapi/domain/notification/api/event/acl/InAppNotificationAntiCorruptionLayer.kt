package com.official.cufitapi.domain.notification.api.event.acl

import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import org.springframework.stereotype.Component

@Component
class InAppNotificationAntiCorruptionLayer(
    private val inAppNotificationCommandACL: List<InAppNotificationAntiCorruptionMapper>,
) {

    fun <T> command(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return inAppNotificationCommandACL.firstOrNull { it.isSupportFeature(inAppNotificationAntiCorruptionLayerDto) }
            ?.let { return it.registerCommand(inAppNotificationAntiCorruptionLayerDto) }
            ?: throw IllegalArgumentException("Not supported inAppNotificationAntiCorruptionLayerDto: $inAppNotificationAntiCorruptionLayerDto")
    }
}
