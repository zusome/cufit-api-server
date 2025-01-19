package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRelationJpaRepository : JpaRepository<MemberRelationEntity, Long> {
    fun findByInviterId(inviterId: Long): List<MemberRelationEntity>
    fun existsByInviterIdAndInviteeId(inviterId: Long, inviteeId: Long): Boolean
}