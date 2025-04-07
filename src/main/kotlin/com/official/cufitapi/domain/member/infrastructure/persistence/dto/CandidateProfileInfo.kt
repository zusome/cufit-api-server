package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import com.official.cufitapi.domain.member.domain.vo.Gender

data class CandidateProfileInfo(
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var city: String? = null,
    var district: String? = null,
    var job: String? = null,
    var yearOfBirth: Int? = null,
    var gender: Gender? = null,
    var phoneNumber: String? = null,
    var hobbies: String? = null,
    var smoke: Int? = null,
    var drink: Int? = null,
) {

    fun isCompleted(): Boolean {
        return idealMbti != null &&
            idealAgeRange != null &&
            idealHeightRange != null &&
            mbti != null &&
            height != null &&
            city != null &&
            district != null &&
            job != null &&
            yearOfBirth != null &&
            gender != null
    }
}
