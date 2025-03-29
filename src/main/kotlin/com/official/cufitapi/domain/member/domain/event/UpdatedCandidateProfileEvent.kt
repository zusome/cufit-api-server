package com.official.cufitapi.domain.member.domain.event

data class UpdatedCandidateProfileEvent(
    val candidateId: Long,
    val memberId: Long
) {
}
