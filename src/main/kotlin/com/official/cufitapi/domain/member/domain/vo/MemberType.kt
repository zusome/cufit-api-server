package com.official.cufitapi.domain.member.domain.vo

enum class MemberType(
    val description: String,
    val code: String,
) {
    ADMIN("관리자", "AD"),
    BASIC("일반 사용자", "BA"),
    CANDIDATE("후보자", "CA"),
    MAKER("주선자", "MA");

    companion object {
        fun ofName(authority: String): MemberType =
            entries.firstOrNull { it.name == (authority) }
                ?: throw IllegalArgumentException("Unknown authority: $authority")

        fun ofCode(code: String): MemberType =
            entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("Unknown code: $code")
    }
}
