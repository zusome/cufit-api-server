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
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MatchCandidates
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.OtherMatchCandidates
import org.springframework.stereotype.Component

@Component
class MakerApiDtoMapper {
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

    fun candidateCount(candidateCount: Long): CandidateCountResponse =
        CandidateCountResponse(candidateCount)

    fun otherCandidateCount(otherCandidateCount: Long): MakerOtherCandidatesCountResponse =
        MakerOtherCandidatesCountResponse(otherCandidateCount)

    fun matchCandidates(matchCandidates: MatchCandidates): MakerCandidatesResponse {
        return matchCandidates.candidates.map(::mapToCandidates).let(::MakerCandidatesResponse)
    }

    fun mapToCandidates(matchCandidate: MatchCandidate): MakerCandidateResponse {
        return MakerCandidateResponse(
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

    fun matchOtherCandidates(otherCandidates: OtherMatchCandidates): MakerOtherCandidatesResponse {
        return otherCandidates.candidates.map(::mapToOtherCandidates).let(::MakerOtherCandidatesResponse)
    }

    fun mapToOtherCandidates(otherMatchCandidate: OtherMatchCandidate): MakerOtherCandidateResponse {
        return MakerOtherCandidateResponse(
            id = otherMatchCandidate.id,
            images = otherMatchCandidate.images.map {
                MakerCandidateImage(
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
            makerRelation = otherMatchCandidate.relation,
            makerName = otherMatchCandidate.makerName,
            idealHeightRange = otherMatchCandidate.idealHeightRange,
            idealAgeRange = otherMatchCandidate.idealAgeRange,
            idealMbti = otherMatchCandidate.idealMbti
        )
    }
}
