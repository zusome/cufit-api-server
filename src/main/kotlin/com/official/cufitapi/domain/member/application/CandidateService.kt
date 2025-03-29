package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.application.command.candidate.CandidateMatchBreakCommand
import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.domain.MatchCandidate
import com.official.cufitapi.domain.member.domain.event.UpdatedCandidateProfileEvent
import com.official.cufitapi.domain.member.domain.repository.MatchCandidateRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface CandidateMatchBreakUseCase {
    fun breakMatch(candidateMatchBreakCommand: CandidateMatchBreakCommand)
}

interface CandidateProfileUpdateUseCase {
    fun updateProfile(candidateProfileUpdateCommand: CandidateProfileUpdateCommand)
}

interface RegisterMatchCandidateUseCase {
    fun register(memberId: Long)
}

@Service
class CandidateService(
    private val matchCandidateRepository: MatchCandidateRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : CandidateProfileUpdateUseCase, CandidateMatchBreakUseCase, RegisterMatchCandidateUseCase {


    override fun register(memberId: Long) {
        matchCandidateRepository.save(MatchCandidate(memberId = memberId))
    }

    override fun updateProfile(command: CandidateProfileUpdateCommand) {
        val matchCandidate = (matchCandidateRepository.findByMemberId(command.memberId))
        matchCandidate.updateProfile(
            images = command.images,
            idealMbti = command.idealMbti,
            idealAgeRange = command.idealAgeRange.joinToString(),
            idealHeightRange = command.idealHeightRange.joinToString(),
            mbti = command.mbti,
            height = command.height,
            station = command.station,
            job = command.job,
            yearOfBirth = command.yearOfBirth,
            gender = command.gender,
            phoneNumber = command.phoneNumber
        )
        matchCandidateRepository.save(matchCandidate)
        applicationEventPublisher.publishEvent(
            UpdatedCandidateProfileEvent(matchCandidate.id!!, matchCandidate.memberId)
        )
    }

    override fun breakMatch(command: CandidateMatchBreakCommand) {
        val matchCandidate = matchCandidateRepository.findByMemberId(command.memberId)
        matchCandidate.breakMatch(command.isMatchAgreed)
        matchCandidateRepository.save(matchCandidate)
    }
}
