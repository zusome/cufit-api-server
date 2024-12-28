package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {

    @Query("SELECT m FROM MemberEntity m JOIN FETCH m.memberAuthorization ma WHERE ma.provider = :provider AND ma.providerId = :providerId")
    fun findByProviderAndProviderId(provider: String, providerId: String): MemberEntity?
}