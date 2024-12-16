package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MakerCandidateRelation
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRelationJpaRepository : JpaRepository<MakerCandidateRelation, Long> {
}