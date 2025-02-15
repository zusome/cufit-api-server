package com.official.cufitapi.domain.member.api.dto.matchmaker

data class MatchMakerCandidateResponse(
    val image: String,
    val name: String,
    val relation: String,
    val arrangements: List<ArrangementResponse>,
    val hasProfile: Boolean,
    val isMatchingPaused: Boolean,
) {
}
