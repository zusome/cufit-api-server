package com.official.cufitapi.domain.notification.api.event.acl.dto

data class RegisterNotificationCommandAntiCorruptionLayerDto<T>(
    val payload: T,
) {
}
