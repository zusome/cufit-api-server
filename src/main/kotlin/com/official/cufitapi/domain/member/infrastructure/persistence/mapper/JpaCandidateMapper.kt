package com.official.cufitapi.domain.member.infrastructure.persistence.mapper

import com.official.cufitapi.domain.member.domain.Candidate
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaCandidate
import com.official.cufitapi.domain.member.infrastructure.persistence.JpaCandidateImage
import org.springframework.stereotype.Component

@Component
class JpaCandidateMapper {

    fun mapToDomain(entity: JpaCandidateImage): CandidateImage = CandidateImage(
        imageUrl = entity.imageUrl,
        profileOrder = entity.profileOrder,
        id = entity.id
    )

    fun mapToDomain(entity: JpaCandidate): Candidate {
        return Candidate(
            memberId = entity.memberId,
            isMatchAgreed = entity.isMatchAgreed,
            images = entity.images.map(::mapToDomain).toMutableList(),
            idealMbti = entity.idealMbti,
            idealAgeRange = entity.idealAgeRange,
            idealHeightRange = entity.idealHeightRange,
            mbti = entity.mbti,
            height = entity.height,
            city = entity.city,
            district = entity.district,
            job = entity.job,
            yearOfBirth = entity.yearOfBirth,
            gender = entity.gender,
            phoneNumber = entity.phoneNumber,
            hobbies = entity.hobbies,
            smoke = entity.smoke,
            drink = entity.drink,
            id = entity.id,
        )
    }

    fun mapToEntity(candidateImage: CandidateImage): JpaCandidateImage {
        return JpaCandidateImage(
            imageUrl = candidateImage.imageUrl,
            profileOrder = candidateImage.profileOrder,
            id = candidateImage.id
        )
    }

    fun mapToEntity(candidate: Candidate): JpaCandidate {
        return JpaCandidate(
            memberId = candidate.memberId,
            isMatchAgreed = candidate.isMatchAgreed,
            images = candidate.images.map(::mapToEntity).toMutableList(),
            idealMbti = candidate.idealMbti,
            idealAgeRange = candidate.idealAgeRange,
            idealHeightRange = candidate.idealHeightRange,
            mbti = candidate.mbti,
            height = candidate.height,
            city = candidate.city,
            district = candidate.district,
            job = candidate.job,
            yearOfBirth = candidate.yearOfBirth,
            gender = candidate.gender,
            phoneNumber = candidate.phoneNumber,
            hobbies = candidate.hobbies,
            smoke = candidate.smoke,
            drink = candidate.drink,
            id = candidate.id
        )
    }
}
