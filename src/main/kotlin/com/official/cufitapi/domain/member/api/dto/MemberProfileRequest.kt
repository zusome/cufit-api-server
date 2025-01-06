package com.official.cufitapi.domain.member.api.dto

import com.official.cufitapi.domain.member.enums.Gender
import com.official.cufitapi.domain.member.enums.IdealAge
import com.official.cufitapi.domain.member.enums.IdealHeightUnit
import com.official.cufitapi.domain.member.enums.MBTILetter
import com.official.cufitapi.domain.member.enums.MemberType

data class MemberProfileRequest(
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