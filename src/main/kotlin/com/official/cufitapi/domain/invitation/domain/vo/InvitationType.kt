package com.official.cufitapi.domain.invitation.domain.vo

enum class InvitationType(
    val description: String,
) {
    ADMIN("관리자"),
    BASIC("일반 사용자"),
    CANDIDATE("후보자"),
    MATCHMAKER("주선자");

    fun suffix(): String {
        return this.name.substring(0, 2)
    }

    companion object {
        fun of(name: String): InvitationType {
            return entries.firstOrNull { it.name == name }
                ?: throw IllegalArgumentException("Invalid invitation type: $name")
        }
    }
}
