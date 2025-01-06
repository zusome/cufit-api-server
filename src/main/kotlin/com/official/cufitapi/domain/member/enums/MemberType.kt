package com.official.cufitapi.domain.member.enums

enum class MemberType(
    val description: String
) {
    ADMIN("관리자"),
    BASIC("일반 사용자"),
    CANDIDATE("후보자"),
    MATCHMAKER("주선자");

    companion object {
        @JvmStatic
        fun invitationCodePrefix(type: MemberType) = when (type) {
            CANDIDATE -> "CA"
            MATCHMAKER -> "MA"
            ADMIN -> "AD"
            BASIC -> "BA"
        }

        fun of(authority: String): MemberType =
            entries.firstOrNull { it.name == (authority) }
                ?: throw IllegalArgumentException("Unknown authority: $authority")
    }
}
