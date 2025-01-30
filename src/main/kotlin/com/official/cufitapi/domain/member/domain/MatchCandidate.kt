package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.Gender

class MatchCandidate(
    val id: Long,
    val memberId: Long,
    val isMatchingAgreed: Boolean,
    var idealMbti: String?,
    var idealAgeRange: String?,
    var idealHeightRange: String?,
    var mbti: String?,
    var height: Int?,
    var station: String?,
    var job: String?,
    var name: String?,
    var yearOfBirth: Int?,
    var email: String?,
    var gender: Gender?,
    var phoneNumber: String?
) {
    fun updateProfile(
        idealMbti: String?,
        idealAgeRange: String?,
        idealHeightRange: String?,
        mbti: String?,
        height: Int?,
        station: String?,
        job: String?,
        name: String?,
        yearOfBirth: Int?,
        email: String?,
        gender: Gender?,
        phoneNumber: String?
    )
    {
        this.idealMbti = idealMbti
        this.idealAgeRange = idealAgeRange
        this.idealHeightRange = idealHeightRange
        this.mbti = mbti
        this.height = height
        this.station = station
        this.job = job
        this.name = name
        this.yearOfBirth = yearOfBirth
        this.email = email
        this.gender = gender
        this.phoneNumber = phoneNumber
    }
}