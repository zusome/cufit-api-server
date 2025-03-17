package com.official.cufitapi.domain.member.domain

class MemberAuthorization(
    val provider: String,
    val providerId: String,
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberAuthorization

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
