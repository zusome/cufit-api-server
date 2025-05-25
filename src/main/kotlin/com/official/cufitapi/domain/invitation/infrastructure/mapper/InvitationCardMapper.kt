package com.official.cufitapi.domain.invitation.infrastructure.mapper

import com.official.cufitapi.domain.invitation.domain.InvitationCard
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType
import com.official.cufitapi.domain.invitation.domain.vo.InvitationType
import com.official.cufitapi.domain.invitation.infrastructure.persistence.JpaInvitationCard
import org.springframework.stereotype.Component

@Component
class InvitationCardMapper {

    fun mapToDomain(entity: JpaInvitationCard): InvitationCard {
        return InvitationCard(
            code = InvitationCode(entity.code),
            invitationType = InvitationType.of(entity.invitationType),
            relationType = InvitationRelationType.of(entity.relationType),
            inviterId = entity.inviterId,
            isAccepted = entity.isAccepted,
            inviteeId = entity.inviteeId,
            id = entity.id
        )
    }

    fun mapToEntity(domain: InvitationCard): JpaInvitationCard =
        JpaInvitationCard(
            code = domain.code.value,
            invitationType = domain.invitationType.value,
            relationType = domain.relationType.name,
            inviterId = domain.inviterId,
            isAccepted = domain.isAccepted,
            inviteeId = domain.inviteeId,
            id = domain.id
        )
}
