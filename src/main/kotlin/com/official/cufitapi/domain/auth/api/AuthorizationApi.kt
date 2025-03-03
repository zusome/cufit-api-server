package com.official.cufitapi.domain.auth.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.docs.AuthApiDocs
import com.official.cufitapi.domain.auth.api.dto.OidcLoginHttpRequest
import com.official.cufitapi.domain.auth.api.dto.OidcLoginHttpResponse
import com.official.cufitapi.domain.auth.api.dto.RefreshLoginHttpRequest
import com.official.cufitapi.domain.auth.api.dto.RefreshLoginHttpResponse
import com.official.cufitapi.domain.auth.api.dto.TestLoginHttpRequest
import com.official.cufitapi.domain.auth.application.AuthorizationTokenCreationUseCase
import com.official.cufitapi.domain.auth.application.AuthorizationTokenRefreshUseCase
import com.official.cufitapi.domain.auth.application.MemberRegistrationUseCase
import com.official.cufitapi.domain.auth.application.OidcProviderIdFindUseCase
import com.official.cufitapi.domain.auth.application.command.AuthorizationTokenCreationCommand
import com.official.cufitapi.domain.auth.application.command.AuthorizationTokenRefreshCommand
import com.official.cufitapi.domain.auth.application.command.MemberRegistrationCommand
import com.official.cufitapi.domain.auth.application.command.OidcProviderIdFindCommand
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import java.util.UUID

@ApiV1Controller
class AuthorizationApi(
    private val oidcProviderIdFindUseCase: OidcProviderIdFindUseCase,
    private val memberRegistrationUseCase: MemberRegistrationUseCase,
    private val authorizationTokenCreationUseCase: AuthorizationTokenCreationUseCase,
    private val authorizationTokenRefreshUseCase: AuthorizationTokenRefreshUseCase,
) : AuthApiDocs {

    @PostMapping("/auth/login/oidc")
    fun loginByOidc(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestBody request: OidcLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse> {
        val idToken = authorization.replace(BEARER, BLANK)
        val providerId = oidcProviderIdFindUseCase.find(OidcProviderIdFindCommand(idToken, request.provider))
        val member = memberRegistrationUseCase.register(
            MemberRegistrationCommand(
                request.username,
                request.email,
                request.provider,
                providerId
            )
        )
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return HttpResponse.of(HttpStatus.OK, OidcLoginHttpResponse(member, authorizationToken))
    }

    @PostMapping("/auth/login/refresh")
    fun loginByOidc(
        @RequestHeader("X-Refresh-Token") refreshToken: String,
        @Authorization(AuthorizationType.ALL, expiredCheck = false) authorizationUser: AuthorizationUser,
        @RequestBody request: RefreshLoginHttpRequest,
    ): HttpResponse<RefreshLoginHttpResponse> {
        val authorizationToken = authorizationTokenRefreshUseCase.refresh(
            AuthorizationTokenRefreshCommand(
                authorizationUser.userId,
                authorizationUser.authority,
                authorizationUser.accessToken,
                refreshToken
            )
        )
        return HttpResponse.of(HttpStatus.OK, RefreshLoginHttpResponse(authorizationToken))
    }

    @PostMapping("/auth/login/test")
    fun loginByOidc(
        @RequestBody request: TestLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse> {
        val member = memberRegistrationUseCase.register(
            MemberRegistrationCommand(
                request.username,
                request.email,
                request.provider,
                UUID.randomUUID().toString()
            )
        )
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return HttpResponse.of(HttpStatus.OK, OidcLoginHttpResponse(member, authorizationToken))
    }

    companion object {
        private const val BEARER = "Bearer "
        private const val BLANK = ""
    }
}
