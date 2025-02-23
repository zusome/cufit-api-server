package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class MatchCandidateDto(
    var memberId: Long,
    var isMatchAgreed: Boolean = true,
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var station: String? = null,
    var job: String? = null,
    var yearOfBirth: Int? = null,
    var gender: String? = null,
    var phoneNumber: String? = null,
    var id: Long,
) {

    fun hasProfile(): Boolean {
        return idealMbti != null && idealAgeRange != null && idealHeightRange != null && height != null && station != null && job != null && yearOfBirth != null
    }
}
