package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.Gender

class MatchCandidate(
    val id: Long,
    val memberId: Long,
    val isMatchAgreed: Boolean,
    var images: List<CandidateImage>,
    var idealMbti: String?,
    var idealAgeRange: String?,
    var idealHeightRange: String?,
    var mbti: String?,
    var height: Int?,
    var station: String?,
    var job: String?,
    var yearOfBirth: Int?,
    var gender: Gender?,
    var phoneNumber: String?,
) {
    fun updateProfile(
        images: List<CandidateImage>,
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
