package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberProfileImageJpaRepository : JpaRepository<MemberProfileImageEntity, Int> {
    fun findAllByMemberId(memberId: Long): List<MemberProfileImageEntity>
}