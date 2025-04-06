package com.official.cufitapi.domain.notification.api.event.acl.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.arrangement.domain.event.MatchedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.RejectedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.SuggestedArrangementEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionMapper
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.NotificationMembers
import org.springframework.stereotype.Component

@Component
class ArrangementEventLeftCandidateAntiCorruptionMapper(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val notificationMembers: NotificationMembers,
) : InAppNotificationAntiCorruptionMapper {

    override fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean {
        val payload = inAppNotificationAntiCorruptionLayerDto.payload
        return (payload is MatchedArrangementEvent || payload is RejectedArrangementEvent || payload is SuggestedArrangementEvent)
    }

    override fun <T> registerCommand(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return when (val payload = inAppNotificationAntiCorruptionLayerDto.payload) {
            is MatchedArrangementEvent -> matchedArrangementEvent(payload)
            is RejectedArrangementEvent -> rejectedArrangementEvent(payload)
            is SuggestedArrangementEvent -> suggestedArrangementEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun matchedArrangementEvent(payload: MatchedArrangementEvent): RegisterInAppNotificationCommand {
        val leftCandidateName = notificationMembers.name(payload.leftCandidateId)
        return RegisterInAppNotificationCommand(
            title = "${leftCandidateName}님과의 매칭 성공",
            body = "${leftCandidateName}님과의 매칭이 성공했어요! 연락처를 확인해 보세요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun rejectedArrangementEvent(payload: RejectedArrangementEvent): RegisterInAppNotificationCommand {
        val leftCandidateName = notificationMembers.name(payload.leftCandidateId)
        return RegisterInAppNotificationCommand(
            title = "${leftCandidateName}님과의 매칭 종료",
            body = "${leftCandidateName}님이 매칭을 거절했어요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun suggestedArrangementEvent(payload: SuggestedArrangementEvent): RegisterInAppNotificationCommand {
        val makerName = notificationMembers.name(payload.makerId)
        val leftCandidateName = notificationMembers.name(payload.leftCandidateId)
        return RegisterInAppNotificationCommand(
            title = "새로운 매칭 요청",
            body = "주선자 ${makerName}님이 ${leftCandidateName}님의 카드를 보냈어요.",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
