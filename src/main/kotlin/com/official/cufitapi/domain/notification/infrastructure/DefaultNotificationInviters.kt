package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRelationRepository
import com.official.cufitapi.domain.notification.domain.NotificationInviters
import org.springframework.stereotype.Component

@Component
class DefaultNotificationInviters(
    private val memberRelationRepository: JpaMemberRelationRepository,
) : NotificationInviters {

    override fun inviterId(inviteeId: Long): Long {
        return memberRelationRepository.findByInviteeId(inviteeId)
            ?.let { return it.inviterId }
            ?: throw IllegalArgumentException("Inviter not found")
    }
}
