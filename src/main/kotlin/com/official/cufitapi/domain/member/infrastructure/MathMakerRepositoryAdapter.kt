package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MatchMaker
import com.official.cufitapi.domain.member.domain.repository.MathMakerRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchMakerJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MathMakerRepositoryAdapter(
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
) : MathMakerRepository {

    @Transactional(readOnly = false)
    override fun save(matchMaker: MatchMaker): MatchMaker {
        val entity = toEntity(matchMaker)
        return toDomain(matchMakerJpaRepository.save(entity))
    }

    private fun toDomain(entity: MatchMakerEntity): MatchMaker {
        return MatchMaker(
            memberId = entity.memberId,
            matchMakerId = entity.id
        )
    }

    private fun toEntity(matchMaker: MatchMaker): MatchMakerEntity =
        MatchMakerEntity(memberId = matchMaker.memberId)
}
