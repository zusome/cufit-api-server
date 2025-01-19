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
            idealMbti = command.idealMbti,
            idealAgeRange = command.idealAgeRange.joinToString(),
            idealHeightRange = command.idealHeightRange.joinToString(),
            mbti = command.mbti,
            height = command.height,
            station = command.station,
            job = command.job,
            name = command.name,
            yearOfBirth = command.yearOfBirth,
            email = command.email,
            gender = command.gender,
            phoneNumber = command.phoneNumber
        )
    }
}