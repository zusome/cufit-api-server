package com.official.cufitapi.common.annotation

enum class AuthorizationType {
    ALL,
    ADMIN,
    BASIC,
    CANDIDATE,
    MAKER,;

    fun isAll(): Boolean =
        this == ALL
}
