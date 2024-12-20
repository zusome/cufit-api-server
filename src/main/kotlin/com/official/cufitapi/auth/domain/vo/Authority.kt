package com.official.cufitapi.auth.domain.vo

enum class Authority(
    val description: String,
) {
    ADMIN("관리자"),
    BASIC("일반 사용자"),
    CANDIDATE("후보자"),
    MATCHMAKER("주선자");
    companion object {
        fun of(authority: String): Authority =
            entries.firstOrNull { it.name == (authority) }
                ?: throw IllegalArgumentException("Unknown authority: $authority")
    }
}
