package com.official.cufitapi.domain.notification.api.event.acl

import com.official.cufitapi.domain.notification.api.event.acl.translator.RegisterNotificationCommandTranslator
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import org.springframework.stereotype.Component

@Component
class RegisterNotificationCommandsAntiCorruptionLayer(
    private val translators: List<RegisterNotificationCommandTranslator>,
) {

    fun <T> commands(dto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): List<RegisterNotificationCommand> {
        return translators.filter { it.isSupport(dto) }
            .map { it.convert(dto) }
    }
}
