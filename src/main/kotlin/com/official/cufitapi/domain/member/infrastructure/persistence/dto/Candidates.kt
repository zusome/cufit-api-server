package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import com.official.cufitapi.domain.member.domain.vo.CandidateImage

data class Candidates(
    val candidates: List<Candidate>,
)

data class Candidate(
    val image: String,
    val name: String,
    val relation: String,
    val matches: List<MatchInfo>,
    val hasProfile: Boolean,
    val isMatchPaused: Boolean,
    val candidateDetailInfo: CandidateDetailInfo,
)

data class CandidateDetailInfo(
    val id: Long,
    val images: List<CandidateImage>,
    val name: String,
    val yearOfBirth: Int,
    val makerRelation: String,
    val makerName: String,
    val mbti: String,
    val height: Int,
    val city: String,
    val district: String,
    val job: String,
    val hobbies: List<String>,
    val smoke: Int,
    val drink: Int,
    val idealHeightRange: List<Int>,
    val idealAgeRange: List<String>,
    val idealMbti: List<String>,
)

data class MatchInfo(
    val image: String,
    val name: String,
    val matchStatus: String,
)

data class OtherCandidates(
    val candidates: List<OtherCandidate>,
)

data class OtherCandidate(
    val id: Long,
    val images: List<CandidateImage>,
    val name: String,
    val yearOfBirth: Int,
    val mbti: String,
    val height: Int,
    val city: String,
    val district: String,
    val job: String,
    val relation: String,
    val makerName: String,
    val idealHeightRange: List<Int>,
    val idealAgeRange: List<String>,
    val idealMbti: List<String>,
    val hobbies: List<String>,
    val smoke: Int,
    val drink: Int
)
