package com.official.cufitapi.domain.notification.api.event.acl.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.match.domain.event.SucceedMatchEvent
import com.official.cufitapi.domain.match.domain.event.RejectedMatchEvent
import com.official.cufitapi.domain.match.domain.event.SuggestedMatchEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionMapper
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.NotificationMembers
import org.springframework.stereotype.Component

@Component
class MatchEventRightCandidateAntiCorruptionMapper(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val notificationMembers: NotificationMembers,
) : InAppNotificationAntiCorruptionMapper {

    override fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean {
        val payload = inAppNotificationAntiCorruptionLayerDto.payload
        return (payload is SucceedMatchEvent || payload is RejectedMatchEvent || payload is SuggestedMatchEvent)
    }

    override fun <T> registerCommand(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return when (val payload = inAppNotificationAntiCorruptionLayerDto.payload) {
            is SucceedMatchEvent -> succeedMatchEvent(payload)
            is RejectedMatchEvent -> rejectedMatchEvent(payload)
            is SuggestedMatchEvent -> suggestedMatchEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun succeedMatchEvent(payload: SucceedMatchEvent): RegisterInAppNotificationCommand {
        val rightCandidateName = notificationMembers.name(payload.rightCandidateId)
        return RegisterInAppNotificationCommand(
            title = "${rightCandidateName}님과의 매칭 성공",
            body = "${rightCandidateName}님과의 매칭이 성공했어요! 연락처를 확인해 보세요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.leftCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun rejectedMatchEvent(payload: RejectedMatchEvent): RegisterInAppNotificationCommand {
        val rightCandidateName = notificationMembers.name(payload.rightCandidateId)
        return RegisterInAppNotificationCommand(
            title = "${rightCandidateName}님과의 매칭 종료",
            body = "${rightCandidateName}님이 매칭을 거절했어요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun suggestedMatchEvent(payload: SuggestedMatchEvent): RegisterInAppNotificationCommand {
        val makerName = notificationMembers.name(payload.makerId)
        val rightCandidateName = notificationMembers.name(payload.rightCandidateId)
        return RegisterInAppNotificationCommand(
            title = "새로운 매칭 요청",
            body = "주선자 ${makerName}님이 ${rightCandidateName}님의 카드를 보냈어요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.leftCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
