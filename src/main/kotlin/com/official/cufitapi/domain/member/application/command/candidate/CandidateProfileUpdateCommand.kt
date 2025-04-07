package com.official.cufitapi.domain.member.application.command.candidate

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.IdealAge
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit

data class CandidateProfileUpdateCommand(
    val images: MutableList<CandidateImage>,
    val memberId: Long,
    val gender: Gender,
    val yearOfBirth: Int,
    val height: Int,
    val job: String,
    val city: String,
    val district: String,
    val mbti: String,
    val idealHeightRange: List<IdealHeightUnit>,
    val idealAgeRange: List<IdealAge>,
    val idealMbti: String,
    val phoneNumber: String,
    val hobbies: List<String>,
    val smoke: Int,
    val drink: Int,
) {
}
