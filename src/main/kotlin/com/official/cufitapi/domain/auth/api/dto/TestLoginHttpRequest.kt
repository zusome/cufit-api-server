package com.official.cufitapi.domain.auth.api.dto

import com.official.cufitapi.domain.auth.application.command.RegisterAuthorizationMemberCommand
import java.util.UUID

data class TestLoginHttpRequest(
    val username: String? = null,
    val email: String? = null,
    val provider: String
) {
    fun toCommand(): RegisterAuthorizationMemberCommand {
        return RegisterAuthorizationMemberCommand(
            username,
            email,
            provider,
            UUID.randomUUID().toString()
        )
    }
}
