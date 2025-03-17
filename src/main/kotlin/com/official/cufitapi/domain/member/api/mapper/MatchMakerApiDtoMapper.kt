package com.official.cufitapi.domain.member.api.mapper

import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.matchmaker.ArrangementResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidateImage
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidateResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerOtherCandidateResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.matchmaker.MatchMakerOtherCandidatesResponse
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidates
import org.springframework.stereotype.Component

@Component
class MatchMakerApiDtoMapper {
    private fun mapToCandidates(jpaMatchCandidate: JpaMatchCandidate) =
        CandidatesInfoResponseDto(
            id = jpaMatchCandidate.id!!,
            memberId = jpaMatchCandidate.memberId,
            isMatchAgreed = jpaMatchCandidate.isMatchAgreed,
            idealMbti = jpaMatchCandidate.idealMbti,
            idealAgeRange = jpaMatchCandidate.idealAgeRange,
            idealHeightRange = jpaMatchCandidate.idealHeightRange,
            height = jpaMatchCandidate.height,
            station = jpaMatchCandidate.station,
            job = jpaMatchCandidate.job,
            yearOfBirth = jpaMatchCandidate.yearOfBirth,
            gender = jpaMatchCandidate.gender?.name,
            phoneNumber = jpaMatchCandidate.phoneNumber
        )

    private fun mapToOtherCandidates(jpaMatchCandidate: JpaMatchCandidate) =
        OtherCandidatesInfoResponseDto(
            id = jpaMatchCandidate.id!!,
            memberId = jpaMatchCandidate.memberId,
            isMatchAgreed = jpaMatchCandidate.isMatchAgreed,
            idealMbti = jpaMatchCandidate.idealMbti,
            idealAgeRange = jpaMatchCandidate.idealAgeRange,
            idealHeightRange = jpaMatchCandidate.idealHeightRange,
            height = jpaMatchCandidate.height,
            station = jpaMatchCandidate.station,
            job = jpaMatchCandidate.job,
            yearOfBirth = jpaMatchCandidate.yearOfBirth,
            gender = jpaMatchCandidate.gender?.name,
            phoneNumber = jpaMatchCandidate.phoneNumber
        )

    fun candidateCount(candidateCount: Long): MatchMakerCandidateCountResponse =
        MatchMakerCandidateCountResponse(candidateCount)

    fun otherCandidateCount(otherCandidateCount: Long): MatchMakerOtherCandidatesCountResponse =
        MatchMakerOtherCandidatesCountResponse(otherCandidateCount)

    fun matchCandidates(matchCandidates: MatchCandidates): MatchMakerCandidatesResponse {
        return matchCandidates.candidates.map(::mapToCandidates).let(::MatchMakerCandidatesResponse)
    }

    fun mapToCandidates(matchCandidate: MatchCandidate): MatchMakerCandidateResponse {
        return MatchMakerCandidateResponse(
            image = matchCandidate.image,
            name = matchCandidate.name,
            relation = matchCandidate.relation,
            arrangements = matchCandidate.arrangements.map {
                ArrangementResponse(
                    image = it.image,
                    name = it.name,
                    arrangementStatus = it.arrangementStatus
                )
            },
            hasProfile = matchCandidate.hasProfile,
            isMatchingPaused = matchCandidate.isMatchingPaused
        )
    }

    fun matchOtherCandidates(otherCandidates: OtherMatchCandidates): MatchMakerOtherCandidatesResponse {
        return otherCandidates.candidates.map(::mapToOtherCandidates).let(::MatchMakerOtherCandidatesResponse)
    }

    fun mapToOtherCandidates(otherMatchCandidate: OtherMatchCandidate): MatchMakerOtherCandidateResponse {
        return MatchMakerOtherCandidateResponse(
            id = otherMatchCandidate.id,
            images = otherMatchCandidate.images.map {
                MatchMakerCandidateImage(
                    imageUrl = it.imageUrl,
                    profileOrder = it.profileOrder
                )
            },
            name = otherMatchCandidate.name,
            yearOfBirth = otherMatchCandidate.yearOfBirth,
            mbti = otherMatchCandidate.mbti,
            height = otherMatchCandidate.height,
            station = otherMatchCandidate.station,
            job = otherMatchCandidate.job,
            matchMakerRelation = otherMatchCandidate.matchMakerRelation,
            matchMakerName = otherMatchCandidate.matchMakerName,
            idealHeightRange = otherMatchCandidate.idealHeightRange,
            idealAgeRange = otherMatchCandidate.idealAgeRange,
            idealMbti = otherMatchCandidate.idealMbti
        )
    }
}
