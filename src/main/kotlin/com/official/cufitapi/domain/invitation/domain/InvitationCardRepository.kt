package com.official.cufitapi.domain.invitation.domain

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode

interface InvitationCardRepository {
    fun save(invitationCard: InvitationCard): InvitationCard
    fun findByCode(code: InvitationCode): InvitationCard
    fun findByCodeOrNull(code: InvitationCode): InvitationCard?
}
