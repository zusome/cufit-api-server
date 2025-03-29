package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMemberRelationRepository : JpaRepository<JpaMemberRelation, Long> {
    fun existsByInviterIdAndInviteeId(inviterId: Long, inviteeId: Long): Boolean
    fun findByInviteeId(inviteeId: Long): JpaMemberRelation?
}
