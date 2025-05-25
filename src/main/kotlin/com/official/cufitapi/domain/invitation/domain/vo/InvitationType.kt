package com.official.cufitapi.domain.invitation.domain.vo

enum class InvitationType {

    ADMIN,
    BASIC,
    CANDIDATE,
    MAKER;

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
