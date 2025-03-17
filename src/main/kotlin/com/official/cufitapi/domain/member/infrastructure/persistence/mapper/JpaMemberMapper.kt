package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.Member
import com.official.cufitapi.domain.member.domain.MemberAuthorization
import com.official.cufitapi.domain.member.domain.vo.MemberType
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMember
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMemberAuthorization
import org.springframework.stereotype.Component

@Component
class JpaMemberMapper {

    fun mapToDomain(entity: JpaMember): Member {
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

    fun mapToEntity(member: Member): JpaMember {
        return JpaMember(
            id = member.id,
            name = member.name,
            email = member.email,
            memberAuthorization = JpaMemberAuthorization(
                provider = member.memberAuthorization.provider,
                providerId = member.memberAuthorization.providerId,
                id = member.memberAuthorization.id
            ),
            memberType = member.memberType.name
        )
    }
}
