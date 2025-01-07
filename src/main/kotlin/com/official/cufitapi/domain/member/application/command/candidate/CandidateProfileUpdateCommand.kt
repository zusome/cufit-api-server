package com.official.cufitapi.domain.member.application.command.candidate

import com.official.cufitapi.domain.member.enums.IdealHeightUnit

data class CandidateProfileUpdateCommand(
    val memberId: Long,
    val name: String,
    val gender: com.official.cufitapi.domain.member.enums.Gender,
    val yearOfBirth: Int,
    val height: Int,
    val job: String,
    val station: String,
    val mbti: String,
    val idealHeightRange: List<IdealHeightUnit>,
    val idealAgeRange: List<com.official.cufitapi.domain.member.enums.IdealAge>,
    val idealMbti: String
) {
}