package com.official.cufitapi.domain.member.application

import com.official.cufitapi.domain.member.application.command.candidate.CandidateMatchBreakCommand
import com.official.cufitapi.domain.member.application.command.candidate.CandidateProfileUpdateCommand
import com.official.cufitapi.domain.member.domain.Candidate
import com.official.cufitapi.domain.member.domain.event.UpdatedCandidateProfileEvent
import com.official.cufitapi.domain.member.domain.repository.CandidateRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

interface CandidateMatchBreakUseCase {
    fun breakMatch(candidateMatchBreakCommand: CandidateMatchBreakCommand)
}

interface CandidateProfileUpdateUseCase {
    fun updateProfile(candidateProfileUpdateCommand: CandidateProfileUpdateCommand)
}

interface RegisterCandidateUseCase {
    fun register(memberId: Long)
}

@Service
class CandidateService(
    private val candidateRepository: CandidateRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : CandidateProfileUpdateUseCase, CandidateMatchBreakUseCase, RegisterCandidateUseCase {


    override fun register(memberId: Long) {
        candidateRepository.save(Candidate(memberId = memberId))
    }

    override fun updateProfile(command: CandidateProfileUpdateCommand) {
        val candidate = (candidateRepository.findByMemberId(command.memberId))
        candidate.updateProfile(
            images = command.images,
            idealMbti = command.idealMbti,
            idealAgeRange = command.idealAgeRange.joinToString(),
            idealHeightRange = command.idealHeightRange.joinToString(),
            mbti = command.mbti,
            height = command.height,
            city = command.city,
            district = command.district,
            job = command.job,
            yearOfBirth = command.yearOfBirth,
            gender = command.gender,
            phoneNumber = command.phoneNumber,
            hobbies = command.hobbies.joinToString(),
            smoke = command.smoke,
            drink = command.drink
        )
        candidateRepository.save(candidate)
        applicationEventPublisher.publishEvent(
            UpdatedCandidateProfileEvent(candidate.id!!, candidate.memberId)
        )
    }

    override fun breakMatch(command: CandidateMatchBreakCommand) {
        val candidate = candidateRepository.findByMemberId(command.memberId)
        candidate.breakMatch(command.isMatchPaused)
        candidateRepository.save(candidate)
    }
}
