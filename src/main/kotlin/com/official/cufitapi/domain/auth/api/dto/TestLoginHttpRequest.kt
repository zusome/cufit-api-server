package com.official.cufitapi.domain.auth.api.dto

data class TestLoginHttpRequest(
    val username: String,
    val email: String,
    val provider: String,
) {
}