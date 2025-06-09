package com.official.cufitapi.domain.member.domain

import com.official.cufitapi.domain.member.domain.vo.CandidateImage
import com.official.cufitapi.domain.member.domain.vo.Gender

class Candidate(
    val memberId: Long,
    var isMatchPaused: Boolean = false,
    var images: MutableList<CandidateImage> = mutableListOf(),
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
    val id: Long? = null,
) {
    fun updateProfile(
        images: MutableList<CandidateImage>,
        idealMbti: String?,
        idealAgeRange: String?,
        idealHeightRange: String?,
        mbti: String?,
        height: Int?,
        city: String?,
        district: String?,
        job: String?,
        yearOfBirth: Int?,
        gender: Gender?,
        phoneNumber: String?,
        hobbies: String?,
        smoke: Int?,
        drink: Int?,
    ) {
        this.images = images
        this.idealMbti = idealMbti
        this.idealAgeRange = idealAgeRange
        this.idealHeightRange = idealHeightRange
        this.mbti = mbti
        this.height = height
        this.city = city
        this.district = district
        this.job = job
        this.yearOfBirth = yearOfBirth
        this.gender = gender
        this.phoneNumber = phoneNumber
        this.hobbies = hobbies
        this.smoke = smoke
        this.drink = drink
    }

    fun breakMatch(matchAgreed: Boolean) {
        this.isMatchPaused = matchAgreed
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Candidate

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
