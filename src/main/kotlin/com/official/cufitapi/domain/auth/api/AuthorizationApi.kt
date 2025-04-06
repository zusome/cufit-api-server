package com.official.cufitapi.domain.auth.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.docs.AuthorizationApiDocs
import com.official.cufitapi.domain.auth.api.dto.OidcLoginHttpRequest
import com.official.cufitapi.domain.auth.api.dto.OidcLoginHttpResponse
import com.official.cufitapi.domain.auth.api.dto.RefreshLoginHttpRequest
import com.official.cufitapi.domain.auth.api.dto.RefreshLoginHttpResponse
import com.official.cufitapi.domain.auth.api.dto.TestLoginHttpRequest
import com.official.cufitapi.domain.auth.application.AuthorizationTokenCreationUseCase
import com.official.cufitapi.domain.auth.application.AuthorizationTokenRefreshUseCase
import com.official.cufitapi.domain.auth.application.FindAuthorizationMemberUseCase
import com.official.cufitapi.domain.auth.application.OidcProviderIdFindUseCase
import com.official.cufitapi.domain.auth.application.RegisterAuthorizationMemberUseCase
import com.official.cufitapi.domain.auth.application.command.AuthorizationTokenCreationCommand
import com.official.cufitapi.domain.auth.application.command.RefreshAuthorizationTokenCommand
import com.official.cufitapi.domain.auth.application.command.OidcProviderIdFindCommand
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthorizationApi(
    private val oidcProviderIdFindUseCase: OidcProviderIdFindUseCase,
    private val registerAuthorizationMemberUseCase: RegisterAuthorizationMemberUseCase,
    private val findAuthorizationMemberUseCase: FindAuthorizationMemberUseCase,
    private val authorizationTokenCreationUseCase: AuthorizationTokenCreationUseCase,
    private val authorizationTokenRefreshUseCase: AuthorizationTokenRefreshUseCase,
) : AuthorizationApiDocs {

    @PostMapping("/auth/login/oidc")
    override fun loginByOidc(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestBody request: OidcLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse> {
        val idToken = authorization.replace(BEARER, BLANK)
        val providerId = oidcProviderIdFindUseCase.find(OidcProviderIdFindCommand(idToken, request.provider))
        val member = registerAuthorizationMemberUseCase.register(request.toCommand(providerId))
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return HttpResponse.of(HttpStatus.OK, OidcLoginHttpResponse(member, authorizationToken))
    }

    @PostMapping("/auth/login/refresh")
    override fun loginByOidc(
        @RequestHeader("X-Refresh-Token") refreshToken: String,
        @Authorization(AuthorizationType.ALL, expiredCheck = false) authorizationUser: AuthorizationUser,
        @RequestBody request: RefreshLoginHttpRequest,
    ): HttpResponse<RefreshLoginHttpResponse> {
        val member = findAuthorizationMemberUseCase.findById(authorizationUser.userId)
        val authorizationToken = authorizationTokenRefreshUseCase.refresh(
            RefreshAuthorizationTokenCommand(authorizationUser.userId, member.authority, refreshToken)
        )
        return HttpResponse.of(HttpStatus.OK, RefreshLoginHttpResponse(member, authorizationToken))
    }

    @PostMapping("/auth/login/test")
    override fun loginByOidc(
        @RequestBody request: TestLoginHttpRequest,
    ): HttpResponse<OidcLoginHttpResponse> {
        val member = registerAuthorizationMemberUseCase.register(request.toCommand())
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return HttpResponse.of(HttpStatus.OK, OidcLoginHttpResponse(member, authorizationToken))
    }

    companion object {
        private const val BEARER = "Bearer "
        private const val BLANK = ""
    }
}
