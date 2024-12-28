package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MakerCandidateRelationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRelationJpaRepository : JpaRepository<MakerCandidateRelationEntity, Long> {
}