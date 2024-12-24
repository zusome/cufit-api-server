package com.official.cufitapi.common.annotation

enum class AuthorizationType {
    ALL,
    ADMIN,
    BASIC,
    CANDIDATE,
    MATCHMAKER,;

    fun isAll(): Boolean =
        this == ALL
}
