package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MatchMakerJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MemberProfileImageJpaRepository
import org.springframework.stereotype.Service


@Service
class MatchMakerService(
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository,
    private val memberProfileImageJpaRepository: MemberProfileImageJpaRepository
) {



}