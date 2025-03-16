package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.Gender

class MatchCandidate(
    val memberId: Long,
    val isMatchAgreed: Boolean = true,
    var images: MutableList<CandidateImage> = mutableListOf(),
    var idealMbti: String? = null,
    var idealAgeRange: String? = null,
    var idealHeightRange: String? = null,
    var mbti: String? = null,
    var height: Int? = null,
    var station: String? = null,
    var job: String? = null,
    var yearOfBirth: Int? = null,
    var gender: Gender? = null,
    var phoneNumber: String? = null,
    val id: Long? = null,
) {
    fun updateProfile(
        images: MutableList<CandidateImage>,
        idealMbti: String?,
        idealAgeRange: String?,
        idealHeightRange: String?,
        mbti: String?,
        height: Int?,
        station: String?,
        job: String?,
        yearOfBirth: Int?,
        gender: Gender?,
        phoneNumber: String?,
    ) {
        this.images = images
        this.idealMbti = idealMbti
        this.idealAgeRange = idealAgeRange
        this.idealHeightRange = idealHeightRange
        this.mbti = mbti
        this.height = height
        this.station = station
        this.job = job
        this.yearOfBirth = yearOfBirth
        this.gender = gender
        this.phoneNumber = phoneNumber
    }
}
