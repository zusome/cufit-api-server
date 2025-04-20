package com.official.cufitapi.domain.notification.api.event.acl.translator

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.match.domain.event.SucceedMatchEvent
import com.official.cufitapi.domain.match.domain.event.RejectedMatchEvent
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.Members
import org.springframework.stereotype.Component

@Component
class MatchEventMakerTranslator(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val members: Members,
) : RegisterNotificationCommandTranslator {

    override fun <T> isSupport(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): Boolean {
        val payload = registerNotificationCommandAntiCorruptionLayerDto.payload
        return (payload is SucceedMatchEvent || payload is RejectedMatchEvent)
    }

    override fun <T> convert(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): RegisterNotificationCommand {
        return when (val payload = registerNotificationCommandAntiCorruptionLayerDto.payload) {
            is SucceedMatchEvent -> succeedMatchEvent(payload)
            is RejectedMatchEvent -> rejectedMatchEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun succeedMatchEvent(payload: SucceedMatchEvent): RegisterNotificationCommand {
        val inviterId = notificationInviters.inviterId(payload.leftCandidateId)
        val isMakerLeft = payload.makerId == inviterId
        val leftName =
            if (isMakerLeft) members.name(payload.leftCandidateId) else members.name(payload.rightCandidateId)
        val rightName =
            if (isMakerLeft) members.name(payload.rightCandidateId) else members.name(payload.leftCandidateId)

        return RegisterNotificationCommand(
            title = "후보자 ${leftName}님 매칭 성공",
            body = "${leftName}님과 ${rightName}님의 매칭이 성공했어요!",
            inAppNotificationType = NotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun rejectedMatchEvent(payload: RejectedMatchEvent): RegisterNotificationCommand {
        val inviterId = notificationInviters.inviterId(payload.leftCandidateId)
        val isMakerLeft = payload.makerId == inviterId
        val isRejectedByLeft = payload.rejectedBy == payload.leftCandidateId
        val leftName =
            if (isMakerLeft) members.name(payload.leftCandidateId) else members.name(payload.rightCandidateId)
        val rightName =
            if (isMakerLeft) members.name(payload.rightCandidateId) else members.name(payload.leftCandidateId)
        return RegisterNotificationCommand(
            title = "후보자 ${leftName}님 매칭 종료",
            body = if (isRejectedByLeft) "${leftName}님이 ${rightName}님을 거절했어요" else "${rightName}님이 ${leftName}님을 거절했어요",
            inAppNotificationType = NotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
