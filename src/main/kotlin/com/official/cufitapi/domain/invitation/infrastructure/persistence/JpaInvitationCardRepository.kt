package com.official.cufitapi.domain.invitation.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaInvitationCardRepository : JpaRepository<JpaInvitationCard, Long> {
    fun findByCode(code: String) : JpaInvitationCard?
}
