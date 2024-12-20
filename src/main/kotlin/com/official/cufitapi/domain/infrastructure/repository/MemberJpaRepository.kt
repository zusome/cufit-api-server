package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberJpaRepository : JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m JOIN FETCH m.memberAuthorization ma WHERE ma.provider = :provider AND ma.providerId = :providerId")
    fun findByProviderAndProviderId(provider: String, providerId: String): Member?
}