package com.official.cufitapi.domain.notification.api.event.acl.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.arrangement.domain.event.MatchedArrangementEvent
import com.official.cufitapi.domain.arrangement.domain.event.RejectedArrangementEvent
import com.official.cufitapi.domain.notification.api.event.acl.InAppNotificationAntiCorruptionMapper
import com.official.cufitapi.domain.notification.api.event.dto.InAppNotificationAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterInAppNotificationCommand
import com.official.cufitapi.domain.notification.application.temp.InAppNotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.NotificationMembers
import org.springframework.stereotype.Component

@Component
class ArrangementEventMatchMakerAntiCorruptionMapper(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val notificationMembers: NotificationMembers,
) : InAppNotificationAntiCorruptionMapper {

    override fun <T> isSupportFeature(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): Boolean {
        val payload = inAppNotificationAntiCorruptionLayerDto.payload
        return (payload is MatchedArrangementEvent || payload is RejectedArrangementEvent)
    }

    override fun <T> registerCommand(inAppNotificationAntiCorruptionLayerDto: InAppNotificationAntiCorruptionLayerDto<T>): RegisterInAppNotificationCommand {
        return when (val payload = inAppNotificationAntiCorruptionLayerDto.payload) {
            is MatchedArrangementEvent -> matchedArrangementEvent(payload)
            is RejectedArrangementEvent -> rejectedArrangementEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun matchedArrangementEvent(payload: MatchedArrangementEvent): RegisterInAppNotificationCommand {
        val inviterId = notificationInviters.inviterId(payload.leftCandidateId)
        val isMakerLeft = payload.matchMakerId == inviterId
        val leftName =
            if (isMakerLeft) notificationMembers.name(payload.leftCandidateId) else notificationMembers.name(payload.rightCandidateId)
        val rightName =
            if (isMakerLeft) notificationMembers.name(payload.rightCandidateId) else notificationMembers.name(payload.leftCandidateId)

        return RegisterInAppNotificationCommand(
            title = "후보자 ${leftName}님 매칭 성공",
            body = "${leftName}님과 ${rightName}님의 매칭이 성공했어요!",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun rejectedArrangementEvent(payload: RejectedArrangementEvent): RegisterInAppNotificationCommand {
        val inviterId = notificationInviters.inviterId(payload.leftCandidateId)
        val isMakerLeft = payload.matchMakerId == inviterId
        val isRejectedByLeft = payload.rejectedBy == payload.leftCandidateId
        val leftName =
            if (isMakerLeft) notificationMembers.name(payload.leftCandidateId) else notificationMembers.name(payload.rightCandidateId)
        val rightName =
            if (isMakerLeft) notificationMembers.name(payload.rightCandidateId) else notificationMembers.name(payload.leftCandidateId)
        return RegisterInAppNotificationCommand(
            title = "후보자 ${leftName}님 매칭 종료",
            body = if (isRejectedByLeft) "${leftName}님이 ${rightName}님을 거절했어요" else "${rightName}님이 ${leftName}님을 거절했어요",
            inAppNotificationType = InAppNotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
