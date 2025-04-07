package com.official.cufitapi.domain.auth.domain.vo

enum class Authority(
    val description: String,
) {
    ADMIN("관리자"),
    BASIC("일반 사용자"),
    CANDIDATE("후보자"),
    MAKER("주선자");

    fun isSameAuthority(name: String): Boolean =
        this.name == name

    companion object {
        fun of(authority: String): Authority =
            entries.firstOrNull { it.name == (authority) }
                ?: throw IllegalArgumentException("Unknown authority: $authority")
    }
}
