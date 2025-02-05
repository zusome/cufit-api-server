package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberAuthorizationJpaRepository : JpaRepository<MemberAuthorizationEntity, Long> {
    fun findByMemberId(memberId: Long): MemberAuthorizationEntity?
}