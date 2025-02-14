package com.official.cufitapi.domain.arrangement.infrastructure

import com.official.cufitapi.domain.arrangement.domain.Arrangement
import com.official.cufitapi.domain.arrangement.domain.ArrangementRepository
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementJpaRepository
import org.springframework.stereotype.Component

@Component
class ArrangementRepositoryAdapter(
    private val arrangementJpaRepository: ArrangementJpaRepository
) : ArrangementRepository {

    override fun findById(id: Long): Arrangement {
        val entity = arrangementJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Arrangement not found") }
        return Arrangement.fromEntity(entity)
    }

}