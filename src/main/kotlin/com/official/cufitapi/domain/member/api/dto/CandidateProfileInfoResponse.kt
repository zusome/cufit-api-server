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
    var station: String? = null,
    var job: String? = null,
    var name: String? = null,
    var yearOfBirth: Int? = null,
    var email: String? = null,
    var gender: Gender? = null,
    var phoneNumber: String? = null,
) {
    constructor(candidateProfileInfo: CandidateProfileInfo) : this(
        candidateProfileInfo.idealMbti,
        candidateProfileInfo.idealAgeRange,
        candidateProfileInfo.idealHeightRange,
        candidateProfileInfo.mbti,
        candidateProfileInfo.height,
        candidateProfileInfo.station,
        candidateProfileInfo.job,
        candidateProfileInfo.name,
        candidateProfileInfo.yearOfBirth,
        candidateProfileInfo.email,
        candidateProfileInfo.gender,
        candidateProfileInfo.phoneNumber
    )
}
