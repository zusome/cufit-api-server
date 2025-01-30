package com.official.cufitapi.domain.member.domain.vo

enum class Gender {
    MALE, FEMALE, OTHER;

    companion object {
        fun of(gender: String?): Gender? =
            entries.firstOrNull { it.name == gender }
    }
}
