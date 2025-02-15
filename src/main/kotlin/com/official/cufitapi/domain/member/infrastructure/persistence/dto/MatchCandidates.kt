package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import com.official.cufitapi.domain.member.domain.vo.CandidateImage

data class MatchCandidates(
    val candidates: List<MatchCandidate>,
)

data class MatchCandidate(
    val image: String,
    val name: String,
    val relation: String,
    val arrangements: List<ArrangementInfo>,
    val hasProfile: Boolean,
    val isMatchingPaused: Boolean,
)

data class ArrangementInfo(
    val image: String,
    val name: String,
    val arrangementStatus: String,
)

data class OtherMatchCandidates(
    val candidates: List<OtherMatchCandidate>,
)

data class OtherMatchCandidate(
    val id: Long,
    val images: List<CandidateImage>,
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
