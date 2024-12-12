package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CandidateService(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) {

    // 후보자 조회
    fun getCandidates() {

    }

    // 후보자 프로필 업데이트
    fun updateProfile() {

    }
}