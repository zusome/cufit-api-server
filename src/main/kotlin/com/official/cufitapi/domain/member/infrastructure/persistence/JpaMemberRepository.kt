package com.official.cufitapi.domain.member.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaMemberRepository : JpaRepository<JpaMember, Long> {

    @Query("SELECT m FROM JpaMember m JOIN FETCH m.memberAuthorization ma WHERE ma.provider = :provider AND ma.providerId = :providerId")
    fun findByProviderAndProviderId(provider: String, providerId: String): JpaMember?
}
