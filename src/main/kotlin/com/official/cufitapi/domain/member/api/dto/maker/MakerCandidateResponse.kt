package com.official.cufitapi.domain.member.api.dto.maker

data class MakerCandidateResponse(
    val image: String,
    val name: String,
    val relation: String,
    val arrangements: List<ArrangementResponse>,
    val hasProfile: Boolean,
    val isMatchingPaused: Boolean,
) {
}
