package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaMemberRelationRepository : JpaRepository<JpaMemberRelation, Long> {
    fun findByInviterIdAndInviteeId(inviterId: Long, inviteeId: Long): JpaMemberRelation?
    fun findByInviteeId(inviteeId: Long): JpaMemberRelation?
}
