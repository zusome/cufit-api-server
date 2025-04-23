package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class CandidateImageDto(
    var imageUrl: String,
    var profileOrder: Int,
    var candidateId: Long,
    var memberId: Long,
    var seq: Long,
) {
}
