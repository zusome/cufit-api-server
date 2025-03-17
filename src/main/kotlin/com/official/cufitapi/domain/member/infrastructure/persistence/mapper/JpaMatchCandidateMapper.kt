package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.MatchCandidate
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaMatchCandidateImage
import org.springframework.stereotype.Component

@Component
class JpaMatchCandidateMapper {

    fun mapToDomain(entity: JpaMatchCandidateImage): CandidateImage = CandidateImage(
        imageUrl = entity.imageUrl,
        profileOrder = entity.profileOrder,
        id = entity.id
    )

    fun mapToDomain(entity: JpaMatchCandidate): MatchCandidate {
        return MatchCandidate(
            memberId = entity.memberId,
            isMatchAgreed = entity.isMatchAgreed,
            images = entity.images.map(::mapToDomain).toMutableList(),
            idealMbti = entity.idealMbti,
            idealAgeRange = entity.idealAgeRange,
            idealHeightRange = entity.idealHeightRange,
            mbti = entity.mbti,
            height = entity.height,
            station = entity.station,
            job = entity.job,
            yearOfBirth = entity.yearOfBirth,
            gender = entity.gender,
            phoneNumber = entity.phoneNumber,
            id = entity.id,
        )
    }

    fun mapToEntity(candidateImage: CandidateImage): JpaMatchCandidateImage {
        return JpaMatchCandidateImage(
            imageUrl = candidateImage.imageUrl,
            profileOrder = candidateImage.profileOrder,
            id = candidateImage.id
        )
    }

    fun mapToEntity(matchCandidate: MatchCandidate): JpaMatchCandidate {
        return JpaMatchCandidate(
            memberId = matchCandidate.memberId,
            isMatchAgreed = matchCandidate.isMatchAgreed,
            images = matchCandidate.images.map(::mapToEntity).toMutableList(),
            idealMbti = matchCandidate.idealMbti,
            idealAgeRange = matchCandidate.idealAgeRange,
            idealHeightRange = matchCandidate.idealHeightRange,
            mbti = matchCandidate.mbti,
            height = matchCandidate.height,
            station = matchCandidate.station,
            job = matchCandidate.job,
            yearOfBirth = matchCandidate.yearOfBirth,
            gender = matchCandidate.gender,
            phoneNumber = matchCandidate.phoneNumber,
            id = matchCandidate.id
        )
    }
}
