package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class MatchDto(
    var makerMemberId: Long,
    var leftCandidateMemberId: Long,
    var rightCandidateMemberId: Long,
    var matchStatus: String,
    var id: Long,
) {
    fun otherCandidateId(candidateId: Long): Long {
        if(leftCandidateMemberId != candidateId && rightCandidateMemberId != candidateId) {
            throw IllegalArgumentException("memberId is not in the match")
        }
        return if (leftCandidateMemberId == candidateId) rightCandidateMemberId else leftCandidateMemberId
    }
}
