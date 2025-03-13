package com.official.cufitapi.domain.invitation.infrastructure.mapper

import com.official.cufitapi.domain.invitation.domain.InvitationCard
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType
import com.official.cufitapi.domain.invitation.infrastructure.persistence.JpaInvitationCard
import org.springframework.stereotype.Component

@Component
class InvitationCardMapper {

    fun mapToDomain(it: JpaInvitationCard): InvitationCard {
        return InvitationCard(
            code = InvitationCode(it.code),
            relationType = InvitationRelationType.of(it.relationType),
            inviterId = it.inviterId,
            isAccepted = it.isAccepted,
            inviteeId = it.inviteeId,
            id = it.id
        )
    }

    fun mapToEntity(invitationCard: InvitationCard): JpaInvitationCard =
        JpaInvitationCard(
            code = invitationCard.code.value,
            relationType = invitationCard.relationType.name,
            inviterId = invitationCard.inviterId,
            isAccepted = invitationCard.isAccepted,
            inviteeId = invitationCard.inviteeId,
            id = invitationCard.id
        )
}
