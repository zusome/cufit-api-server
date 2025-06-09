package com.official.cufitapi.domain.member.infrastructure.persistence.dto

data class CandidateDto(
    var memberId: Long,
    var isMatchPaused: Boolean = false,
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var city: String? = null,
    var district: String? = null,
    var job: String? = null,
    var yearOfBirth: Int? = null,
    var gender: String? = null,
    var phoneNumber: String? = null,
    var hobbies: String? = null,
    var smoke: Int? = null,
    var drink: Int? = null,
    var id: Long,
) {

    fun hasProfile(): Boolean {
        return idealMbti != null && idealAgeRange != null && idealHeightRange != null && height != null && city != null && district != null && job != null && yearOfBirth != null
    }
}
