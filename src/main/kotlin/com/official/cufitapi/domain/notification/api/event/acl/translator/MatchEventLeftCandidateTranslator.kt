package com.official.cufitapi.domain.notification.api.event.acl.translator

import com.fasterxml.jackson.databind.ObjectMapper
import com.official.cufitapi.domain.match.domain.event.SucceedMatchEvent
import com.official.cufitapi.domain.match.domain.event.RejectedMatchEvent
import com.official.cufitapi.domain.match.domain.event.SuggestedMatchEvent
import com.official.cufitapi.domain.notification.api.event.acl.dto.RegisterNotificationCommandAntiCorruptionLayerDto
import com.official.cufitapi.domain.notification.application.command.RegisterNotificationCommand
import com.official.cufitapi.domain.notification.domain.notification.vo.NotificationType
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import com.official.cufitapi.domain.notification.domain.Members
import org.springframework.stereotype.Component

@Component
class MatchEventLeftCandidateTranslator(
    private val objectMapper: ObjectMapper,
    private val notificationInviters: NotificationInviters,
    private val members: Members,
) : RegisterNotificationCommandTranslator {

    override fun <T> isSupport(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): Boolean {
        val payload = registerNotificationCommandAntiCorruptionLayerDto.payload
        return (payload is SucceedMatchEvent || payload is RejectedMatchEvent || payload is SuggestedMatchEvent)
    }

    override fun <T> convert(registerNotificationCommandAntiCorruptionLayerDto: RegisterNotificationCommandAntiCorruptionLayerDto<T>): RegisterNotificationCommand {
        return when (val payload = registerNotificationCommandAntiCorruptionLayerDto.payload) {
            is SucceedMatchEvent -> succeedMatchEvent(payload)
            is RejectedMatchEvent -> rejectedMatchEvent(payload)
            is SuggestedMatchEvent -> suggestedMatchEvent(payload)
            else -> throw IllegalArgumentException("Unsupported event type")
        }
    }

    private fun succeedMatchEvent(payload: SucceedMatchEvent): RegisterNotificationCommand {
        val leftCandidateName = members.name(payload.leftCandidateId)
        return RegisterNotificationCommand(
            title = "${leftCandidateName}님과의 매칭 성공",
            body = "${leftCandidateName}님과의 매칭이 성공했어요! 연락처를 확인해 보세요.",
            inAppNotificationType = NotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun rejectedMatchEvent(payload: RejectedMatchEvent): RegisterNotificationCommand {
        val leftCandidateName = members.name(payload.leftCandidateId)
        return RegisterNotificationCommand(
            title = "${leftCandidateName}님과의 매칭 종료",
            body = "${leftCandidateName}님이 매칭을 거절했어요.",
            inAppNotificationType = NotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }

    private fun suggestedMatchEvent(payload: SuggestedMatchEvent): RegisterNotificationCommand {
        val makerName = members.name(payload.makerId)
        val leftCandidateName = members.name(payload.leftCandidateId)
        return RegisterNotificationCommand(
            title = "새로운 매칭 요청",
            body = "주선자 ${makerName}님이 ${leftCandidateName}님의 카드를 보냈어요.",
            inAppNotificationType = NotificationType.MATCHING.name,
            receiverId = notificationInviters.inviterId(payload.rightCandidateId),
            payload = objectMapper.writeValueAsString(payload)
        )
    }
}
