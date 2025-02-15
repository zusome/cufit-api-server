package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class MatchCandidateImageDto(
    var imageUrl: String,
    var profileOrder: Int,
    var matchCandidateId: Long,
    var id: Long,
) {
}
