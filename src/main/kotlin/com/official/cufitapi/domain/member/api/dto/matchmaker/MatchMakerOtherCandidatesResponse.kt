package com.official.cufitapi.domain.member.api.dto.matchmaker

data class MatchMakerOtherCandidatesResponse(
    val candidates: List<MatchMakerOtherCandidateResponse>
)

data class MatchMakerOtherCandidateResponse(
    val id: Long,
    val images: List<MatchMakerCandidateImage>,
    val name: String,
    val yearOfBirth: Int,
    val mbti: String,
    val height: Int,
    val station: String,
    val job: String,
    val degrees: Int,
    val matchMakerRelation: String,
    val matchMakerName: String,
    val idealHeightRange: List<Int>,
    val idealAgeRange: List<String>,
    val idealMbti: List<String>,
)
