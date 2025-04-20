package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.Maker
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMaker
import org.springframework.stereotype.Component

@Component
class JpaMakerMapper {

    fun mapToDomain(entity: JpaMaker): Maker {
        return Maker(
            memberId = entity.memberId,
            makerId = entity.id
        )
    }

    fun mapToEntity(maker: Maker): JpaMaker =
        JpaMaker(memberId = maker.memberId)
}
