package com.official.cufitapi.domain.infrastructure.repository

import com.official.cufitapi.domain.infrastructure.entity.MatchMaker
import org.springframework.data.jpa.repository.JpaRepository

interface MatchMakerJpaRepository : JpaRepository<MatchMaker, Long> {
}