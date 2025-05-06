package com.official.cufitapi.acceptance.auth

import com.official.cufitapi.acceptance.AcceptanceTest
import com.official.cufitapi.acceptance.auth.AuthAcceptanceStep.회원가입한다
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class AuthAcceptanceTest : AcceptanceTest() {

    @Nested
    inner class `회원가입` {

        @Test
        fun `회원가입을한다`() {
            val response = 회원가입한다()
            val responseBody = response.jsonPath()

            assertAll(
                { assertThat(responseBody.getString("code")).isEqualTo("200") },
                { assertThat(responseBody.getString("message")).isEqualTo("OK") },
                { assertThat(responseBody.getMap<String, String>("payload")["accessToken"]).isNotBlank() },
                { assertThat(responseBody.getMap<String, String>("payload")["refreshToken"]).isNotBlank() },
                { assertThat(responseBody.getMap<String, String>("payload")["memberType"]).isNotBlank() }
            )
        }

        @Test
        fun `중복 이메일로 회원가입을 할 수 없다`() {
            회원가입한다(email = "conflict@test.com")
            val 회원가입실패회원 = 회원가입한다(email = "conflict@test.com")
            val responseBody = 회원가입실패회원.jsonPath()

            assertAll(
                { assertThat(responseBody.getString("code")).isEqualTo("RUNTIME_EXCEPTION") },
                { assertThat(responseBody.getString("message")).isEqualTo("서버 내부 오류입니다.") },
            )
        }
    }
}

