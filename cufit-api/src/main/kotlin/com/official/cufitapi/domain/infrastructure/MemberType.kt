package com.official.cufitapi.domain.infrastructure

enum class MemberType(
    val description: String
) {
    CANDIDATE("후보자"),
    MATCHMAKER("주선자");

    companion object {
        @JvmStatic
        fun invitationCodePrefix(type: MemberType) = when (type) {
            CANDIDATE -> "CA"
            MATCHMAKER -> "MA"
        }
    }
}