package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.infrastructure.repository.CandidateJpaRepository
import com.official.cufitapi.domain.infrastructure.repository.MatchMakerJpaRepository
import org.springframework.stereotype.Service


@Service
class MatchMakerService(
    private val matchMakerJpaRepository: MatchMakerJpaRepository,
    private val candidateJpaRepository: CandidateJpaRepository
) {

    //
    fun getCandidates(matchMakerId: Long) {
        val candidates = candidateJpaRepository.findByMatchMakerId(matchMakerId)
    }

}