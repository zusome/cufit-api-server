package com.official.cufitapi.domain.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MatchRelationJpaRepository : JpaRepository<MakerCandidateRelationEntity, Long> {
}