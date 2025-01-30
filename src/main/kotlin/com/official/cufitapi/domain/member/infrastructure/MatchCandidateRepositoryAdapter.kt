package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MatchCandidate
import com.official.cufitapi.domain.member.domain.repository.MatchCandidateRepository
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import org.springframework.stereotype.Component

@Component
class MatchCandidateRepositoryAdapter(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository
) : MatchCandidateRepository {

    override fun findByMemberId(memberId: Long): MatchCandidate {
        val entity = matchCandidateJpaRepository.findByMemberId(memberId)
            ?: throw RuntimeException("후보자가 아닙니다.")
        return MatchCandidate(
            id = entity.id ?: throw RuntimeException(),
            isMatchingAgreed = entity.isMatchingAgreed,
            memberId = entity.member.id ?: throw RuntimeException(),
            idealMbti = entity.idealMbti,
            idealAgeRange = entity.idealAgeRange,
            idealHeightRange = entity.idealHeightRange,
            mbti = entity.mbti,
            height = entity.height,
            station = entity.station,
            job = entity.job,
            name = entity.name,
            yearOfBirth = entity.yearOfBirth,
            email = entity.email,
            gender = entity.gender,
            phoneNumber = entity.phoneNumber
        )
    }

    override fun matchBreak(matchCandidate: MatchCandidate, isMatchingAgreed: Boolean) {
        val entity = matchCandidateJpaRepository.findById(matchCandidate.id)
            .orElseThrow { RuntimeException() }
        entity.updateMatchingAgreement(isMatchingAgreed)
    }

    override fun updateProfile(matchCandidate: MatchCandidate) {
        val entity = matchCandidateJpaRepository.findById(matchCandidate.id)
            .orElseThrow { RuntimeException() }
            entity.updateProfile(
                idealMbti = matchCandidate.idealMbti ?: throw RuntimeException(),
                idealAgeRange = matchCandidate.idealAgeRange ?: throw RuntimeException(),
                idealHeightRange = matchCandidate.idealHeightRange ?: throw RuntimeException(),
                mbti = matchCandidate.mbti ?: throw RuntimeException(),
                height = matchCandidate.height ?: throw RuntimeException(),
                station = matchCandidate.station ?: throw RuntimeException(),
                job = matchCandidate.job ?: throw RuntimeException(),
                name = matchCandidate.name ?: throw RuntimeException(),
                yearOfBirth = matchCandidate.yearOfBirth ?: throw RuntimeException(),
                email = matchCandidate.email ?: throw RuntimeException(),
                gender = matchCandidate.gender ?: throw RuntimeException(),
                phoneNumber = matchCandidate.phoneNumber ?: throw RuntimeException())
        }
    }