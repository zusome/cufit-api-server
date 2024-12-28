package com.official.cufitapi.domain.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface InvitationJpaRepository : JpaRepository<InvitationEntity, Long> {

    fun existsBySenderIdAndCodeAndIsActivatedIsTrue(memberId: Long, code: String) : Boolean
    fun findByCode(code: String) : InvitationEntity?
    fun findBySenderId(memberId: Long) : InvitationEntity?
}