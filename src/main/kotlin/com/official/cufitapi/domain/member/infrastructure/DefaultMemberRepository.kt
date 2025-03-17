package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.repository.MemberRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMemberMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMemberRepository(
    private val jpaMemberMapper: JpaMemberMapper,
    private val jpaMemberRepository: JpaMemberRepository,
) : MemberRepository {

    @Transactional(readOnly = false)
    override fun save(member: Member): Member {
        val entity = jpaMemberMapper.mapToEntity(member)
        return jpaMemberMapper.mapToDomain(jpaMemberRepository.save(entity))
    }

    @Transactional(readOnly = true)
    override fun findById(memberId: Long): Member = this.findByIdOrNull(memberId)
        ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)

    @Transactional(readOnly = true)
    override fun findByIdOrNull(memberId: Long): Member? =
        jpaMemberRepository.findByIdOrNull(memberId)
            ?.let(jpaMemberMapper::mapToDomain)

    @Transactional(readOnly = true)
    override fun findByProviderAndProviderId(provider: String, providerId: String): Member? =
        jpaMemberRepository.findByProviderAndProviderId(provider, providerId)
            ?.let(jpaMemberMapper::mapToDomain)
}
