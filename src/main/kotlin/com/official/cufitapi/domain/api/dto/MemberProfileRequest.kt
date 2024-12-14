package com.official.cufitapi.domain.api.dto

import com.official.cufitapi.domain.enums.*

data class MemberProfileRequest (
    val memberType: MemberType,
    val name: String,
    val gender: Gender,
    val yearOfBirth: Int,
    val location: String,
    val height: Int,
    val MBTI: String,
    val idealHeightRange: List<IdealHeightUnit>,
    val idealAgeRange: List<IdealAge>,
    val idealMbti: List<MBTILetter>
)