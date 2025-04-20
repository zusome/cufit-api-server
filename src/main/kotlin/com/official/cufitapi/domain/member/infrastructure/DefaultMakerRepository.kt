package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.Maker
import com.official.cufitapi.domain.member.domain.repository.MakerRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMakerRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.mapper.JpaMakerMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultMakerRepository(
    private val jpaMakerMapper: JpaMakerMapper,
    private val jpaMakerRepository: JpaMakerRepository,
) : MakerRepository {

    @Transactional(readOnly = false)
    override fun save(maker: Maker): Maker {
        val entity = jpaMakerMapper.mapToEntity(maker)
        return jpaMakerMapper.mapToDomain(jpaMakerRepository.save(entity))
    }
}
