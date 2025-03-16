package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.repository.MemberRepository
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MemberJpaRepository
import org.springframework.stereotype.Component

@Component
class MemberRepositoryAdapter(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {
    override fun update(member: Member): Member {
        TODO("Not yet implemented")
    }

    override fun findById(memberId: Long) : Member {
        val entity = memberJpaRepository.findById(memberId)
            .orElseThrow { NotFoundException(ErrorCode.NOT_FOUND_MEMBER) }
        return mapToDomain(entity)
    }

    private fun mapToDomain(entity: MemberEntity): Member {
        return Member(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            memberType = MemberType.valueOf(entity.memberType.name)
        )
    }
}