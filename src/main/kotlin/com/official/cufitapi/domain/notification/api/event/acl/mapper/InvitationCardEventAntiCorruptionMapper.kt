package com.official.cufitapi.domain.notification.api.event.acl.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.event.RegisteredInvitationCardEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionMapper
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import com.official.cufitapi.domain.notification.domain.NotificationMembers
import org.springframework.stereotype.Component

@Component
class InvitationCardEventAntiCorruptionMapper(
    private val objectMapper: ObjectMapper,
    private val notificationMembers: NotificationMembers,
) : InAppNotificationAntiCorruptionMapper {

    override fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean {
        val payload = inAppNotificationAntiCorruptionLayerDto.payload
        return (payload is RegisteredInvitationCardEvent || payload is AcceptedInvitationCardEvent)
    }

    override fun <T> registerCommand(dto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return when (val payload = dto.payload) {
            is RegisteredInvitationCardEvent -> registerInAppNotification(payload)
            is AcceptedInvitationCardEvent -> acceptInAppNotification(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun registerInAppNotification(payload: RegisteredInvitationCardEvent) =
        RegisterInAppNotificationCommand(
            "후보자 초대장 생성 완료",
            "24시간 이내로 초대 코드를 공유해 보세요!",
            InAppNotificationType.CANDIDATE.name,
            payload.inviterId,
            objectMapper.writeValueAsString(payload)
        )

    private fun acceptInAppNotification(payload: AcceptedInvitationCardEvent): RegisterInAppNotificationCommand {
        val name = notificationMembers.name(payload.inviteeId)
        return RegisterInAppNotificationCommand(
            "후보자 ${name}님 입장 완료",
            "${name}님이 큐핏에 들어왔어요.",
            InAppNotificationType.CANDIDATE.name,
            payload.inviterId,
            objectMapper.writeValueAsString(payload)
        )
    }
}
