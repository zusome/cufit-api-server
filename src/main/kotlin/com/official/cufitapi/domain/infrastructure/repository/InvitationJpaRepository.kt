package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.Invitation
import org.springframework.data.jpa.repository.JpaRepository

interface InvitationJpaRepository : JpaRepository<Invitation, Long> {

    fun existsBySenderIdAndCodeAndIsActivatedIsTrue(memberId: Long, code: String) : Boolean
    fun findByCode(code: String) : Invitation?
    fun findBySenderId(memberId: Long) : Invitation?
}