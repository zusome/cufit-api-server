package com.official.cufitapi.domain.member.api.dto

import com.official.cufitapi.domain.member.domain.vo.Gender
import com.official.cufitapi.domain.member.domain.vo.IdealAge
import com.official.cufitapi.domain.member.domain.vo.IdealHeightUnit
import com.official.cufitapi.domain.member.domain.vo.MBTILetter
import com.official.cufitapi.domain.member.domain.vo.MemberType

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