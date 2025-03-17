package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.MemberAuthorization
import com.official.cufitapi.domain.member.domain.repository.MemberRepository
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberAuthorizationEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberRepositoryAdapter(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {

    @Transactional(readOnly = false)
    override fun save(member: Member): Member {
        val entity = mapToEntity(member)
        return mapToDomain(memberJpaRepository.save(entity))
    }

    @Transactional(readOnly = true)
    override fun findById(memberId: Long): Member =
        this.findByIdOrNull(memberId) ?: throw NotFoundException(ErrorCode.NOT_FOUND_MEMBER)

    @Transactional(readOnly = true)
    override fun findByIdOrNull(memberId: Long): Member? =
        memberJpaRepository.findByIdOrNull(memberId)?.let { mapToDomain(it) }

    @Transactional(readOnly = true)
    override fun findByProviderAndProviderId(provider: String, providerId: String): Member? =
        memberJpaRepository.findByProviderAndProviderId(provider, providerId)
            ?.let { mapToDomain(it) }

    private fun mapToDomain(entity: MemberEntity): Member {
        return Member(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            memberAuthorization = entity.memberAuthorization.let {
                MemberAuthorization(
                    provider = it.provider,
                    providerId = it.providerId,
                    id = it.id
                )
            },
            memberType = MemberType.ofName(entity.memberType)
        )
    }

    private fun mapToEntity(member: Member): MemberEntity {
        return MemberEntity(
            id = member.id,
            name = member.name,
            email = member.email,
            memberAuthorization = MemberAuthorizationEntity(
                provider = member.memberAuthorization.provider,
                providerId = member.memberAuthorization.providerId,
                id = member.memberAuthorization.id
            ),
            memberType = member.memberType.name
        )
    }
}
