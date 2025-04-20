package com.official.cufitapi.domain.notification.api.event.acl.translator

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.member.domain.event.UpdatedCandidateProfileEvent
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.Members
import org.springframework.stereotype.Component

@Component
class CandidateTranslator(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val members: Members,
) : RegisterNotificationCommandTranslator {

    override fun <T> isSupport(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): Boolean {
        val payload = registerNotificationCommandAntiCorruptionLayerDto.payload
        return (payload is UpdatedCandidateProfileEvent)
    }

    override fun <T> convert(dto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): RegisterNotificationCommand {
        return when (val payload = dto.payload) {
            is UpdatedCandidateProfileEvent -> updatedCandidateProfileEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun updatedCandidateProfileEvent(payload: UpdatedCandidateProfileEvent): RegisterNotificationCommand {
        val name = members.name(payload.memberId)
        return RegisterNotificationCommand(
            title = "후보자 ${name}님 프로필 작성 완료",
            body = "${name}님에게 첫 매칭 요청을 보내보세요",
            inAppNotificationType = NotificationType.CANDIDATE.name,
            receiverId = notificationInviters.inviterId(inviteeId = payload.memberId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
