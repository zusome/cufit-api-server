package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import java.time.LocalDateTime

data class MatchDto(
    var makerMemberId: Long,
    var leftCandidateMemberId: Long,
    var leftCandidateAgree: Boolean,
    var rightCandidateMemberId: Long,
    var rightCandidateAgree: Boolean,
    var matchStatus: String,
    var createdDate: LocalDateTime,
    var modifiedDate: LocalDateTime,
    var id: Long,
) {
    fun otherCandidateId(candidateId: Long): Long {
        if(leftCandidateMemberId != candidateId && rightCandidateMemberId != candidateId) {
            throw IllegalArgumentException("memberId is not in the match")
        }
        return if (leftCandidateMemberId == candidateId) rightCandidateMemberId else leftCandidateMemberId
    }
}
