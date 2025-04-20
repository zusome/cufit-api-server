package com.official.cufitapi.domain.notification.api.event.acl

import com.official.cufitapi.domain.notification.api.event.acl.translator.RegisterNotificationCommandTranslator
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import org.springframework.stereotype.Component

@Component
class RegisterNotificationCommandAntiCorruptionLayer(
    private val translators: List<RegisterNotificationCommandTranslator>,
) {

    fun <T> command(dto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): RegisterNotificationCommand {
        return translators.firstOrNull { it.isSupport(dto) }
            ?.convert(dto)
            ?: throw IllegalArgumentException("Not supported inAppNotificationAntiCorruptionLayerDto: $dto")
    }
}
