package com.official.cufitapi.domain.notification.api.event.acl.translator

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.invitation.domain.event.AcceptedInvitationCardEvent
import com.official.cufitapi.domain.invitation.domain.event.RegisteredInvitationCardEvent
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType
import com.official.cufitapi.domain.notification.domain.Members
import org.springframework.stereotype.Component

@Component
class InvitationCardTranslator(
    private val objectMapper: ObjectMapper,
    private val members: Members,
) : RegisterNotificationCommandTranslator {

    override fun <T> isSupport(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): Boolean {
        val payload = registerNotificationCommandAntiCorruptionLayerDto.payload
        return (payload is RegisteredInvitationCardEvent || payload is AcceptedInvitationCardEvent)
    }

    override fun <T> convert(dto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): RegisterNotificationCommand {
        return when (val payload = dto.payload) {
            is RegisteredInvitationCardEvent -> registerInAppNotification(payload)
            is AcceptedInvitationCardEvent -> acceptInAppNotification(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun registerInAppNotification(payload: RegisteredInvitationCardEvent) =
        RegisterNotificationCommand(
            title = "후보자 초대장 생성 완료",
            body = "24시간 이내로 초대 코드를 공유해 보세요!",
            inAppNotificationType = NotificationType.CANDIDATE.name,
            receiverId = payload.inviterId,
            payload = objectMapper.writeValueAsString(payload)
        )

    private fun acceptInAppNotification(payload: AcceptedInvitationCardEvent): RegisterNotificationCommand {
        val name = members.name(payload.inviteeId)
        return RegisterNotificationCommand(
            title = "후보자 ${name}님 입장 완료",
            body = "${name}님이 큐핏에 들어왔어요.",
            inAppNotificationType = NotificationType.CANDIDATE.name,
            receiverId = payload.inviterId,
            payload =  objectMapper.writeValueAsString(payload)
        )
    }
}
