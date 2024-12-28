package com.official.cufitapi.domain.application

import com.official.cufitapi.domain.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.infrastructure.repository.MatchCandidateJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CandidateProfileUpdateUseCase {
    fun update(candidateProfileUpdateCommand: CandidateProfileUpdateCommand)
}

@Service
@Transactional(readOnly = true)
class CandidateService(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) : CandidateProfileUpdateUseCase {

    // 후보자 조회
    fun getCandidates() {

    }

    // 후보자 프로필 업데이트
    override fun update(command: CandidateProfileUpdateCommand) {
        // TODO : DB 저장
        // TODO : recommendation 호출
    }
}