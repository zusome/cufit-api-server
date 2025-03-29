package com.official.cufitapi.domain.notification.api.event.acl.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.member.domain.event.UpdatedCandidateProfileEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionMapper
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.NotificationMembers
import org.springframework.stereotype.Component

@Component
class CandidateEventAntiCorruptionMapper(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val notificationMembers: NotificationMembers,
) : InAppNotificationAntiCorruptionMapper {

    override fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean {
        val payload = inAppNotificationAntiCorruptionLayerDto.payload
        return (payload is UpdatedCandidateProfileEvent)
    }

    override fun <T> registerCommand(dto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return when (val payload = dto.payload) {
            is UpdatedCandidateProfileEvent -> updatedCandidateProfileEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun updatedCandidateProfileEvent(payload: UpdatedCandidateProfileEvent): RegisterInAppNotificationCommand {
        val name = notificationMembers.name(payload.memberId)
        return RegisterInAppNotificationCommand(
            title = "후보자 ${name}님 프로필 작성 완료",
            body = "${name}님에게 첫 매칭 요청을 보내보세요",
            inAppNotificationType = InAppNotificationType.CANDIDATE.name,
            receiverId = notificationInviters.inviterId(inviteeId = payload.memberId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
