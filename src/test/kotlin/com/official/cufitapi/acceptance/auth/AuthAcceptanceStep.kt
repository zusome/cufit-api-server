package com.official.cufitapi.acceptance.auth

import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response

object AuthAcceptanceStep {

    fun 회원가입한다(
        username: String = "tester",
        email: String = "test@test.com",
        provider: String = "apple",
    ): Response = Given {
        contentType(ContentType.JSON)
        header("Authorization", "Bearer test")
        body(
            """
            {
                "userIdentifier": "",
                "username": "$username",
                "email": "$email",
                "provider": "$provider"
            }
            """.trimIndent()
        )
    } When {
        post("/api/v1/auth/login/oidc")
    } Then {
        statusCode(200)
        contentType(ContentType.JSON)
    } Extract {
        response()
    }
}
