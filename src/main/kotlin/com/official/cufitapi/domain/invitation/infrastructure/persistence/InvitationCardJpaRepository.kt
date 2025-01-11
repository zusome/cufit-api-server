package com.official.cufitapi.domain.invitation.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface InvitationCardJpaRepository : JpaRepository<InvitationCardEntity, Long> {
    fun findByCode(code: String) : InvitationCardEntity?
    fun findByInviterId(inviterId: Long) : InvitationCardEntity?
    fun findAllByInviterId(inviterId: Long) : List<InvitationCardEntity>
}