package com.official.cufitapi.domain.member.api.dto.candidate

data class OtherCandidatesInfoResponseDto(
    val id: Long,
    val memberId: Long,
    val isMatchAgreed: Boolean,
    val idealMbti: String? = null,
    val idealAgeRange: String? = null,
    val idealHeightRange: String? = null,
    val height: Int? = null,
    val city: String? = null,
    val district: String? = null,
    val job: String? = null,
    val name: String? = null,
    val yearOfBirth: Int? = null,
    val email: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val hobbies: String? = null,
    val smoke: Int? = null,
    val drink: Int? = null,
)
