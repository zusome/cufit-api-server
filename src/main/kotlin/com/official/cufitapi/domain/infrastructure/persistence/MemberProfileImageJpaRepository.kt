package com.official.cufitapi.domain.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberProfileImageJpaRepository : JpaRepository<MemberProfileImageEntity, Int> {
    fun findAllByMemberId(memberId: Long): List<MemberProfileImageEntity>
}