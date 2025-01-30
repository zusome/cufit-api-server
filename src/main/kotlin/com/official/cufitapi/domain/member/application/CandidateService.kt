package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.application.command.candidate.CandidateMatchBreakCommand
import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.domain.repository.MatchCandidateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CandidateMatchBreakUseCase {
    fun breakMatch(candidateMatchBreakCommand: CandidateMatchBreakCommand)
}

interface CandidateProfileUpdateUseCase {
    fun updateProfile(candidateProfileUpdateCommand: CandidateProfileUpdateCommand)
}

@Service
@Transactional(readOnly = true)
class CandidateService(
    private val matchCandidateRepository: MatchCandidateRepository,
) : CandidateProfileUpdateUseCase, CandidateMatchBreakUseCase {

    @Transactional
    override fun updateProfile(command: CandidateProfileUpdateCommand) {
        val matchCandidate = (matchCandidateRepository.findByMemberId(command.memberId))
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

    @Transactional
    override fun breakMatch(command: CandidateMatchBreakCommand) {
        val matchCandidate = matchCandidateRepository.findByMemberId(command.memberId)
        matchCandidateRepository.matchBreak(matchCandidate, command.isMatchingAgreed)
    }
}