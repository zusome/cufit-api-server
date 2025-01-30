package com.official.cufitapi.domain.member.infrastructure.persistence.dto

import com.official.cufitapi.domain.member.domain.vo.Gender

data class CandidateProfileInfo(
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var station: String? = null,
    var job: String? = null,
    var name: String? = null,
    var yearOfBirth: Int? = null,
    var email: String? = null,
    var gender: Gender? = null,
    var phoneNumber: String? = null,
) {

    fun isCompleted(): Boolean {
        return idealMbti != null &&
            idealAgeRange != null &&
            idealHeightRange != null &&
            mbti != null &&
            height != null &&
            station != null &&
            job != null &&
            name != null &&
            yearOfBirth != null &&
            gender != null &&
            email != null
    }
}
