package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CandidateProfileUpdateUseCase {
    fun updateProfile(candidateProfileUpdateCommand: CandidateProfileUpdateCommand)
}

@Service
@Transactional(readOnly = true)
class CandidateService(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) : CandidateProfileUpdateUseCase {

    @Transactional
    override fun updateProfile(command: CandidateProfileUpdateCommand) {
        val matchCandidate = (matchCandidateJpaRepository.findByMemberId(command.memberId)
            ?: throw IllegalArgumentException("후보자가 아닙니다."))
        matchCandidate.updateProfile(
            command.idealMbti,
            command.idealAgeRange.joinToString(),
            command.idealHeightRange.joinToString(),
            command.gender,
            command.height,
            command.station,
            command.job,
            command.name,
            command.yearOfBirth,
            command.email,
            command.phoneNumber
        )
    }
}