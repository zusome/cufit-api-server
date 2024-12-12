package com.official.cufitapi.domain.api.dto

data class MemberProfileRequest (
    val name: String,
    val gender: String,
    val age: BirthDate,
    val location: String,
    val height: Int,
    val MBTI: String,
    val idealType: IdealType
)

data class BirthDate(
    val year: Int,
    val month: Int,
    val day: Int
)

data class IdealType(
    val preference: Preference,
    val disPreference: DisPreference
)

data class Preference(
    val ageRange: AgeRange,
    val heightRange: HeightRange,
    val MBTI: String
)

data class DisPreference(
    val MBTI: String,
    val heightRange: String,
    val ageRange: String
)

data class AgeRange(
    val older: Boolean,
    val same: Boolean,
    val younger: Boolean
)

data class HeightRange(
    val min: Int,
    val max: Int
)
