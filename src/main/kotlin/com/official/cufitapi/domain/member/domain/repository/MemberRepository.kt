package com.official.cufitapi.domain.member.domain.repository

import com.official.cufitapi.domain.member.domain.Member

interface MemberRepository {
    fun save(member: Member): Member
    fun findById(memberId: Long): Member
    fun findByProviderAndProviderId(provider: String, providerId: String): Member?
    fun findByIdOrNull(memberId: Long): Member?
}
