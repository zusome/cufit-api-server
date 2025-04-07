package com.official.cufitapi.domain.member.api.dto.maker

data class MakerOtherCandidatesResponse(
    val candidates: List<MakerOtherCandidateResponse>
)

data class MakerOtherCandidateResponse(
    val id: Long,
    val images: List<MakerCandidateImage>,
    val name: String,
    val yearOfBirth: Int,
    val mbti: String,
    val height: Int,
    val city: String,
    val district: String,
    val job: String,
    val makerRelation: String,
    val makerName: String,
    val idealHeightRange: List<Int>,
    val idealAgeRange: List<String>,
    val idealMbti: List<String>,
    val hobbies: List<String>,
    val smoke: Int,
    val drink: Int,
)
