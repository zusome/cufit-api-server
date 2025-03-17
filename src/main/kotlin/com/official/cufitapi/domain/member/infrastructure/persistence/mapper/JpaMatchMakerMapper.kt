package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.MatchMaker
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchMaker
import org.springframework.stereotype.Component

@Component
class JpaMatchMakerMapper {

    fun mapToDomain(entity: JpaMatchMaker): MatchMaker {
        return MatchMaker(
            memberId = entity.memberId,
            matchMakerId = entity.id
        )
    }

    fun mapToEntity(matchMaker: MatchMaker): JpaMatchMaker =
        JpaMatchMaker(memberId = matchMaker.memberId)
}
