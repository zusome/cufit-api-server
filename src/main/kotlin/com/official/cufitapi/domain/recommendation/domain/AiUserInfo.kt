package com.official.cufitapi.domain.recommendation.domain

import com.official.cufitapi.domain.member.domain.vo.Gender

data class AiUserInfo(
    val id: Long,
    val memberId: Long,
    val isMatchAgreed: Boolean,
    val idealMbti: String?,
    val idealAgeRange: String?,
    val idealHeightRange: String?,
    val mbti: String?,
    val height: Int?,
    val station: String?,
    val job: String?,
    val yearOfBirth: Int?,
    val gender: Gender?,
    val phoneNumber: String?
)

