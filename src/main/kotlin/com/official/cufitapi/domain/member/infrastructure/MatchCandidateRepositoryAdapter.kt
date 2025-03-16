package com.official.cufitapi.domain.member.infrastructure

import com.official.cufitapi.domain.member.domain.MatchCandidate
import com.official.cufitapi.domain.member.domain.repository.MatchCandidateRepository
import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateImageEntity
import com.official.cufitapi.domain.member.infrastructure.persistence.MatchCandidateJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MatchCandidateRepositoryAdapter(
    private val matchCandidateJpaRepository: MatchCandidateJpaRepository,
) : MatchCandidateRepository {

    @Transactional(readOnly = false)
    override fun save(matchCandidate: MatchCandidate): MatchCandidate {
        val entity = toEntity(matchCandidate)
        return toDomain(matchCandidateJpaRepository.save(entity))
    }

    private fun toDomain(entity: MatchCandidateImageEntity): CandidateImage = CandidateImage(
        imageUrl = entity.imageUrl,
        profileOrder = entity.profileOrder,
        id = entity.id
    )

    private fun toDomain(entity: MatchCandidateEntity): MatchCandidate {
        return MatchCandidate(
            id = entity.id,
            images = entity.images.map(::toDomain).toMutableList(),
            isMatchAgreed = entity.isMatchAgreed,
            memberId = entity.memberId,
            idealMbti = entity.idealMbti,
            idealAgeRange = entity.idealAgeRange,
            idealHeightRange = entity.idealHeightRange,
            mbti = entity.mbti,
            height = entity.height,
            station = entity.station,
            job = entity.job,
            yearOfBirth = entity.yearOfBirth,
        )
    }

    private fun toEntity(candidateImage: CandidateImage): MatchCandidateImageEntity {
        return MatchCandidateImageEntity(
            imageUrl = candidateImage.imageUrl,
            profileOrder = candidateImage.profileOrder,
            id = candidateImage.id
        )
    }

    private fun toEntity(matchCandidate: MatchCandidate): MatchCandidateEntity {
        return MatchCandidateEntity(
            memberId = matchCandidate.memberId,
            images = matchCandidate.images.map(::toEntity).toMutableList(),
            idealMbti = matchCandidate.idealMbti,
            idealAgeRange = matchCandidate.idealAgeRange,
            idealHeightRange = matchCandidate.idealHeightRange,
            mbti = matchCandidate.mbti,
            height = matchCandidate.height,
            station = matchCandidate.station,
            job = matchCandidate.job,
            yearOfBirth = matchCandidate.yearOfBirth
        )
    }

    override fun findByMemberId(memberId: Long): MatchCandidate {
        val entity = matchCandidateJpaRepository.findByMemberId(memberId)
            ?: throw RuntimeException("후보자가 아닙니다.")
        return MatchCandidate(
            id = entity.id ?: throw RuntimeException(),
            images = entity.images.map(::toDomain).toMutableList(),
            isMatchAgreed = entity.isMatchAgreed,
            memberId = entity.memberId,
            idealMbti = entity.idealMbti,
            idealAgeRange = entity.idealAgeRange,
            idealHeightRange = entity.idealHeightRange,
            mbti = entity.mbti,
            height = entity.height,
            station = entity.station,
            job = entity.job,
            yearOfBirth = entity.yearOfBirth,
            gender = entity.gender,
            phoneNumber = entity.phoneNumber
        )
    }

    @Transactional(readOnly = false)
    override fun matchBreak(matchCandidate: MatchCandidate, isMatchAgreed: Boolean) {
        val entity = matchCandidateJpaRepository.findById(matchCandidate.id!!)
            .orElseThrow { RuntimeException() }
        entity.updateMatchingAgreement(isMatchAgreed)
    }

    @Transactional(readOnly = false)
    override fun updateProfile(matchCandidate: MatchCandidate) {
        val entity = matchCandidateJpaRepository.findById(matchCandidate.id!!)
            .orElseThrow { RuntimeException() }
        val matchCandidateImageEntities = matchCandidate.images.map { image -> matchCandidateImageEntity(image) }
        entity.updateProfile(
            images = matchCandidateImageEntities,
            idealMbti = matchCandidate.idealMbti ?: throw RuntimeException(),
            idealAgeRange = matchCandidate.idealAgeRange ?: throw RuntimeException(),
            idealHeightRange = matchCandidate.idealHeightRange ?: throw RuntimeException(),
            mbti = matchCandidate.mbti ?: throw RuntimeException(),
            height = matchCandidate.height ?: throw RuntimeException(),
            station = matchCandidate.station ?: throw RuntimeException(),
            job = matchCandidate.job ?: throw RuntimeException(),
            yearOfBirth = matchCandidate.yearOfBirth ?: throw RuntimeException(),
            gender = matchCandidate.gender ?: throw RuntimeException(),
            phoneNumber = matchCandidate.phoneNumber ?: throw RuntimeException()
        )
    }

    private fun matchCandidateImageEntity(image: CandidateImage): MatchCandidateImageEntity =
        MatchCandidateImageEntity(imageUrl = image.imageUrl, profileOrder = image.profileOrder)
}
