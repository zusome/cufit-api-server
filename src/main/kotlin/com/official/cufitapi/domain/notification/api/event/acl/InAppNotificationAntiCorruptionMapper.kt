package com.official.cufitapi.domain.notification.api.event.acl

import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand

interface InAppNotificationAntiCorruptionMapper {
    fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean
    fun <T> registerCommand(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand
}
