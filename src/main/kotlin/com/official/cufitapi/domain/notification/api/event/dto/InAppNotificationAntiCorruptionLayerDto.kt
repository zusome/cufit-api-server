package com.official.cufitapi.domain.notification.api.event.dto

data class InAppNotificationAntiCorruptionLayerDto<T>(
    val payload: T,
) {
}
