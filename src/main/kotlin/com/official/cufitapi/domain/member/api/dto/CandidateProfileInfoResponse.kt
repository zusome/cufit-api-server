package com.official.cufitapi.domain.member.api.dto

import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.CandidateProfileInfo

data class CandidateProfileInfoResponse(
    val isCompleted: Boolean,
    val profile: CandidateProfileResponse,
)

data class CandidateProfileResponse(
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var city: String? = null,
    var district: String? = null,
    var job: String? = null,
    var yearOfBirth: Int? = null,
    var gender: Gender? = null,
    var phoneNumber: String? = null,
    var hobbies: String? = null,
    var smoke: Int? = null,
    var drink: Int? = null,
) {
    constructor(candidateProfileInfo: CandidateProfileInfo) : this(
        candidateProfileInfo.idealMbti,
        candidateProfileInfo.idealAgeRange,
        candidateProfileInfo.idealHeightRange,
        candidateProfileInfo.mbti,
        candidateProfileInfo.height,
        candidateProfileInfo.city,
        candidateProfileInfo.district,
        candidateProfileInfo.job,
        candidateProfileInfo.yearOfBirth,
        candidateProfileInfo.gender,
        candidateProfileInfo.phoneNumber,
        candidateProfileInfo.hobbies,
        candidateProfileInfo.smoke,
        candidateProfileInfo.drink
    )
}
