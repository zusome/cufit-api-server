package com.official.cufitapi.domain.auth.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.docs.AuthApiDocs
import com.official.cufitapi.domain.auth.api.dto.SmsAuthValidationRequest
import com.official.cufitapi.domain.auth.application.AuthenticationSmsService
import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationIssueCommand
import com.official.cufitapi.domain.auth.application.command.SmsAuthenticationValidationCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class AuthenticationSmsApi(
    private val authorizationSmsService: AuthenticationSmsService
) : AuthApiDocs {

    @PostMapping("/auth/sms/issue")
    fun issueSmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser
    ): HttpResponse<Unit> {
        authorizationSmsService.issueSmsAuthCode(SmsAuthenticationIssueCommand(authorizationUser.userId, ""))
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @PostMapping("/auth/sms/validation")
    fun validateSmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: SmsAuthValidationRequest
    ): HttpResponse<Unit> {
        authorizationSmsService.validateSmsAuthCode(
            SmsAuthenticationValidationCommand(
                authorizationUser.userId,
                request.authCode
            )
        )
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }
}
