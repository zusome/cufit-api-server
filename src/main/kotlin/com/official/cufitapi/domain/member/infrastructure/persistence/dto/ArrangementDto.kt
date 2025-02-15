package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class ArrangementDto(
    var matchMakerMemberId: Long,
    var leftCandidateMemberId: Long,
    var rightCandidateMemberId: Long,
    var arrangementStatus: String,
    var id: Long,
) {
    fun otherCandidateId(candidateId: Long): Long {
        if(leftCandidateMemberId != candidateId && rightCandidateMemberId != candidateId) {
            throw IllegalArgumentException("memberId is not in the arrangement")
        }
        return if (leftCandidateMemberId == candidateId) rightCandidateMemberId else leftCandidateMemberId
    }
}
