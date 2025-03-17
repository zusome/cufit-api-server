package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MatchMaker
import com.official.cufitapi.domain.member.domain.repository.MathMakerRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchMakerRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMatchMakerMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMathMakerRepository(
    private val jpaMatchMakerMapper: JpaMatchMakerMapper,
    private val jpaMatchMakerRepository: JpaMatchMakerRepository,
) : MathMakerRepository {

    @Transactional(readOnly = false)
    override fun save(matchMaker: MatchMaker): MatchMaker {
        val entity = jpaMatchMakerMapper.mapToEntity(matchMaker)
        return jpaMatchMakerMapper.mapToDomain(jpaMatchMakerRepository.save(entity))
    }
}
