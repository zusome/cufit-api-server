package com.official.cufitapi.domain.member.application.command.candidate

import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.IdealAge
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit

data class CandidateProfileUpdateCommand(
    val memberId: Long,
    val name: String,
    val gender: Gender,
    val yearOfBirth: Int,
    val height: Int,
    val job: String,
    val station: String,
    val mbti: String,
    val idealHeightRange: List<IdealHeightUnit>,
    val idealAgeRange: List<IdealAge>,
    val idealMbti: String
) {
}