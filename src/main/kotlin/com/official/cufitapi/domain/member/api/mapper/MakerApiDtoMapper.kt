package com.official.cufitapi.domain.member.api.mapper

import com.official.cufitapi.domain.member.api.dto.candidate.CandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.candidate.OtherCandidatesInfoResponseDto
import com.official.cufitapi.domain.member.api.dto.maker.MatchResponse
import com.official.cufitapi.domain.member.api.dto.maker.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidateImage
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidateDetailInfo
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidateResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidateResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesResponse
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
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
            isMatchPaused = jpaCandidate.isMatchPaused,
            idealMbti = jpaCandidate.idealMbti,
            idealAgeRange = jpaCandidate.idealAgeRange,
            idealHeightRange = jpaCandidate.idealHeightRange,
            height = jpaCandidate.height,
            city = jpaCandidate.city,
            district = jpaCandidate.district,
            job = jpaCandidate.job,
            yearOfBirth = jpaCandidate.yearOfBirth,
            gender = jpaCandidate.gender?.name,
            phoneNumber = jpaCandidate.phoneNumber,
            hobbies = jpaCandidate.hobbies,
            smoke = jpaCandidate.smoke,
            drink = jpaCandidate.drink
        )

    private fun mapToOtherCandidates(jpaCandidate: JpaCandidate) =
        OtherCandidatesInfoResponseDto(
            id = jpaCandidate.id!!,
            memberId = jpaCandidate.memberId,
            isMatchPaused = jpaCandidate.isMatchPaused,
            idealMbti = jpaCandidate.idealMbti,
            idealAgeRange = jpaCandidate.idealAgeRange,
            idealHeightRange = jpaCandidate.idealHeightRange,
            height = jpaCandidate.height,
            city = jpaCandidate.city,
            district = jpaCandidate.district,
            job = jpaCandidate.job,
            yearOfBirth = jpaCandidate.yearOfBirth,
            gender = jpaCandidate.gender?.name,
            phoneNumber = jpaCandidate.phoneNumber,
            hobbies = jpaCandidate.hobbies,
            smoke = jpaCandidate.smoke,
            drink = jpaCandidate.drink
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
            matches = candidate.matches.map {
                MatchResponse(
                    image = it.image,
                    name = it.name,
                    matchStatus = it.matchStatus
                )
            },
            hasProfile = candidate.hasProfile,
            isMatchPaused = candidate.isMatchPaused,
            candidateDetailInfo = MakerCandidateDetailInfo(
                id = candidate.candidateDetailInfo.id,
                images = candidate.candidateDetailInfo.images.map {
                    CandidateImage(
                        imageUrl = it.imageUrl,
                        profileOrder = it.profileOrder
                    )
                },
                name = candidate.candidateDetailInfo.name,
                yearOfBirth = candidate.candidateDetailInfo.yearOfBirth,
                makerRelation = candidate.candidateDetailInfo.makerRelation,
                makerName = candidate.candidateDetailInfo.makerName,
                mbti = candidate.candidateDetailInfo.mbti,
                height = candidate.candidateDetailInfo.height,
                city = candidate.candidateDetailInfo.city,
                district = candidate.candidateDetailInfo.district,
                job = candidate.candidateDetailInfo.job,
                hobbies = candidate.candidateDetailInfo.hobbies,
                smoke = candidate.candidateDetailInfo.smoke,
                drink = candidate.candidateDetailInfo.drink,
                idealHeightRange = candidate.candidateDetailInfo.idealHeightRange,
                idealAgeRange = candidate.candidateDetailInfo.idealAgeRange,
                idealMbti = candidate.candidateDetailInfo.idealMbti
            )
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
            city = otherCandidate.city,
            district = otherCandidate.district,
            job = otherCandidate.job,
            makerRelation = otherCandidate.relation,
            makerName = otherCandidate.makerName,
            idealHeightRange = otherCandidate.idealHeightRange,
            idealAgeRange = otherCandidate.idealAgeRange,
            idealMbti = otherCandidate.idealMbti,
            hobbies = otherCandidate.hobbies,
            smoke = otherCandidate.smoke,
            drink = otherCandidate.drink
        )
    }
}
