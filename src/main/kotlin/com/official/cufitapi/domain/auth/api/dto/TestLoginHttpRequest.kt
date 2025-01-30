package com.official.cufitapi.domain.auth.api.dto

data class TestLoginHttpRequest(
    val username: String? = null,
    val email: String? = null,
    val provider: String
) {
}
