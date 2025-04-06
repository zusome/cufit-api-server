package com.official.cufitapi.domain.member.api.mapper

import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.maker.ArrangementResponse
import com.official.cufitapi.domain.member.api.dto.maker.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidateImage
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidateResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidateResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesResponse
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.Candidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherCandidates
import org.springframework.stereotype.Component

@Component
class MakerApiDtoMapper {
    private fun mapToCandidates(jpaCandidate: JpaCandidate) =
        CandidatesInfoResponseDto(
            id = jpaCandidate.id!!,
            memberId = jpaCandidate.memberId,
            isMatchAgreed = jpaCandidate.isMatchAgreed,
            idealMbti = jpaCandidate.idealMbti,
            idealAgeRange = jpaCandidate.idealAgeRange,
            idealHeightRange = jpaCandidate.idealHeightRange,
            height = jpaCandidate.height,
            station = jpaCandidate.station,
            job = jpaCandidate.job,
            yearOfBirth = jpaCandidate.yearOfBirth,
            gender = jpaCandidate.gender?.name,
            phoneNumber = jpaCandidate.phoneNumber
        )

    private fun mapToOtherCandidates(jpaCandidate: JpaCandidate) =
        OtherCandidatesInfoResponseDto(
            id = jpaCandidate.id!!,
            memberId = jpaCandidate.memberId,
            isMatchAgreed = jpaCandidate.isMatchAgreed,
            idealMbti = jpaCandidate.idealMbti,
            idealAgeRange = jpaCandidate.idealAgeRange,
            idealHeightRange = jpaCandidate.idealHeightRange,
            height = jpaCandidate.height,
            station = jpaCandidate.station,
            job = jpaCandidate.job,
            yearOfBirth = jpaCandidate.yearOfBirth,
            gender = jpaCandidate.gender?.name,
            phoneNumber = jpaCandidate.phoneNumber
        )

    fun candidateCount(candidateCount: Long): CandidateCountResponse =
        CandidateCountResponse(candidateCount)

    fun otherCandidateCount(otherCandidateCount: Long): MakerOtherCandidatesCountResponse =
        MakerOtherCandidatesCountResponse(otherCandidateCount)

    fun candidates(candidates: Candidates): MakerCandidatesResponse {
        return candidates.candidates.map(::mapToCandidates).let(::MakerCandidatesResponse)
    }

    fun mapToCandidates(candidate: Candidate): MakerCandidateResponse {
        return MakerCandidateResponse(
            image = candidate.image,
            name = candidate.name,
            relation = candidate.relation,
            arrangements = candidate.arrangements.map {
                ArrangementResponse(
                    image = it.image,
                    name = it.name,
                    arrangementStatus = it.arrangementStatus
                )
            },
            hasProfile = candidate.hasProfile,
            isMatchingPaused = candidate.isMatchingPaused
        )
    }

    fun matchOtherCandidates(otherCandidates: OtherCandidates): MakerOtherCandidatesResponse {
        return otherCandidates.candidates.map(::mapToOtherCandidates).let(::MakerOtherCandidatesResponse)
    }

    fun mapToOtherCandidates(otherCandidate: OtherCandidate): MakerOtherCandidateResponse {
        return MakerOtherCandidateResponse(
            id = otherCandidate.id,
            images = otherCandidate.images.map {
                MakerCandidateImage(
                    imageUrl = it.imageUrl,
                    profileOrder = it.profileOrder
                )
            },
            name = otherCandidate.name,
            yearOfBirth = otherCandidate.yearOfBirth,
            mbti = otherCandidate.mbti,
            height = otherCandidate.height,
            station = otherCandidate.station,
            job = otherCandidate.job,
            makerRelation = otherCandidate.relation,
            makerName = otherCandidate.makerName,
            idealHeightRange = otherCandidate.idealHeightRange,
            idealAgeRange = otherCandidate.idealAgeRange,
            idealMbti = otherCandidate.idealMbti
        )
    }
}
