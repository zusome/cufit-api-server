package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRelationRepository
import com.official.cufitapi.domain.notification.domain.Inviters
import org.springframework.stereotype.Component

@Component
class DefaultInviters(
    private val memberRelationRepository: JpaMemberRelationRepository,
) : Inviters {

    override fun inviterId(inviteeId: Long): Long {
        return memberRelationRepository.findByInviteeId(inviteeId)
            ?.let { return it.inviterId }
            ?: throw IllegalArgumentException("Inviter not found")
    }
}
