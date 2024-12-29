package com.official.cufitapi.auth.api

import com.official.cufitapi.auth.api.dto.OidcLoginHttpRequest
import com.official.cufitapi.auth.api.dto.OidcLoginHttpResponse
import com.official.cufitapi.auth.application.OidcProviderIdFindUseCase
import com.official.cufitapi.auth.application.AuthorizationTokenCreationUseCase
import com.official.cufitapi.auth.application.MemberRegistrationUseCase
import com.official.cufitapi.auth.application.command.AuthorizationTokenCreationCommand
import com.official.cufitapi.auth.application.command.MemberRegistrationCommand
import com.official.cufitapi.auth.application.command.OidcProviderIdFindCommand
import com.official.cufitapi.domain.api.ApiV1Controller
import com.official.cufitapi.domain.api.docs.AuthApiDocs
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@ApiV1Controller
class AuthorizationApi(
    private val oidcProviderIdFindUseCase: OidcProviderIdFindUseCase,
    private val memberRegistrationUseCase: MemberRegistrationUseCase,
    private val authorizationTokenCreationUseCase: AuthorizationTokenCreationUseCase
): AuthApiDocs {

    @PostMapping("/auth/login/oidc")
    fun loginByOidc(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestBody request: OidcLoginHttpRequest
    ): ResponseEntity<OidcLoginHttpResponse> {
        log.info("Authorization: $authorization")
        log.info("request: $request")
        log.info("request filed: email = ${request.email}, provider = ${request.provider}, username = ${request.username}, userIdentifier = ${request.userIdentifier}")
        val idToken = authorization.replace(BEARER, BLANK)
        val providerId = oidcProviderIdFindUseCase.find(OidcProviderIdFindCommand(idToken, request.provider))
        val member = memberRegistrationUseCase.register(MemberRegistrationCommand(request.username, request.email, request.provider, providerId))
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return ResponseEntity.ok(OidcLoginHttpResponse(member, authorizationToken))
    }

    companion object {
        private val log = LoggerFactory.getLogger(AuthorizationApi::class.java)
        private const val BEARER = "Bearer "
        private const val BLANK = ""
    }
}
