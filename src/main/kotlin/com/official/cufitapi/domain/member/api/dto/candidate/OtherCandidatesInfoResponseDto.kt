package com.official.cufitapi.domain.member.api.dto.candidate

data class OtherCandidatesInfoResponseDto(
    val id: Long,
    val memberId: Long,
    val isMatchingAgreed: Boolean,
    val idealMbti: String? = null,
    val idealAgeRange: String? = null,
    val idealHeightRange: String? = null,
    val height: Int? = null,
    val station: String? = null,
    val job: String? = null,
    val name: String? = null,
    val yearOfBirth: Int? = null,
    val email: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
)