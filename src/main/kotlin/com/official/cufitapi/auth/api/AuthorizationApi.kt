package com.official.cufitapi.auth.api

import com.official.cufitapi.auth.api.dto.OidcLoginHttpRequest
import com.official.cufitapi.auth.api.dto.OidcLoginHttpResponse
import com.official.cufitapi.auth.application.OidcProviderIdFindUseCase
import com.official.cufitapi.auth.application.AuthorizationTokenCreationUseCase
import com.official.cufitapi.auth.application.MemberRegistrationUseCase
import com.official.cufitapi.auth.application.command.AuthorizationTokenCreationCommand
import com.official.cufitapi.auth.application.command.MemberRegistrationCommand
import com.official.cufitapi.auth.application.command.OidcProviderIdFindCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorizationApi(
    private val oidcProviderIdFindUseCase: OidcProviderIdFindUseCase,
    private val memberRegistrationUseCase: MemberRegistrationUseCase,
    private val authorizationTokenCreationUseCase: AuthorizationTokenCreationUseCase
) {

    @PostMapping("/login/oidc")
    fun loginByOidc(
        @RequestBody request: OidcLoginHttpRequest
    ): ResponseEntity<OidcLoginHttpResponse> {
        val providerId = oidcProviderIdFindUseCase.find(OidcProviderIdFindCommand(request.idToken, request.provider))
        val member = memberRegistrationUseCase.register(MemberRegistrationCommand(request.name, request.email, request.provider, providerId))
        val authorizationToken = authorizationTokenCreationUseCase.create(AuthorizationTokenCreationCommand(member))
        return ResponseEntity.ok(OidcLoginHttpResponse(member, authorizationToken))
    }
}
