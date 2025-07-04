package com.official.cufitapi.domain.auth.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.dto.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "인증 관련 API")
interface AuthorizationApiDocs {
    @PostMapping("/auth/login/oidc")
    fun loginByOidc(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestBody request: OidcLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse>

    @PostMapping("/auth/login/refresh")
    fun loginByOidc(
        @Authorization(AuthorizationType.ALL, expiredCheck = false) authorizationUser: AuthorizationUser,
        @RequestBody request: RefreshTokenRequest
    ): HttpResponse<RefreshLoginHttpResponse>

    @PostMapping("/auth/login/test")
    fun loginByOidc(
        @RequestBody request: TestLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse>
}
