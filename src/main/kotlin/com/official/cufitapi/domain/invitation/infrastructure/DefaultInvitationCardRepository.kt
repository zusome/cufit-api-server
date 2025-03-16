package com.official.cufitapi.domain.invitation.infrastructure

import com.official.cufitapi.domain.invitation.domain.InvitationCard
import com.official.cufitapi.domain.invitation.domain.InvitationCardRepository
import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.infrastructure.mapper.InvitationCardMapper
import com.official.cufitapi.domain.invitation.infrastructure.persistence.JpaInvitationCardRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultInvitationCardRepository(
    private val invitationCardMapper: InvitationCardMapper,
    private val jpaInvitationCardRepository: JpaInvitationCardRepository,
) : InvitationCardRepository {

    @Transactional(readOnly = false)
    override fun save(invitationCard: InvitationCard): InvitationCard {
        val entity = invitationCardMapper.mapToEntity(invitationCard)
        return jpaInvitationCardRepository.save(entity)
            .let(invitationCardMapper::mapToDomain)
    }

    @Transactional(readOnly = true)
    override fun findByCode(code: InvitationCode): InvitationCard {
        return findByCodeOrNull(code) ?: throw IllegalStateException("InvitationCard not found")
    }

    @Transactional(readOnly = true)
    override fun findByCodeOrNull(code: InvitationCode): InvitationCard? {
        return jpaInvitationCardRepository.findByCode(code.value)?.let(invitationCardMapper::mapToDomain)
    }
}
