package com.official.cufitapi.domain.auth.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.docs.SmsAuthenticationApiDocs
import com.official.cufitapi.domain.auth.api.dto.SmsAuthCodeIssueRequest
import com.official.cufitapi.domain.auth.api.dto.VerifySmsAuthenticationRequest
import com.official.cufitapi.domain.auth.api.dto.VerifySmsAuthenticationResponse
import com.official.cufitapi.domain.auth.application.SmsAuthenticationService
import com.official.cufitapi.domain.auth.application.command.IssueSmsAuthenticationCommand
import com.official.cufitapi.domain.auth.application.command.VerifySmsAuthenticationCodeCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class SmsAuthenticationApi(
    private val smsAuthenticationService: SmsAuthenticationService
) : SmsAuthenticationApiDocs {

    @PostMapping("/auth/sms/issue")
    override fun issueSmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: SmsAuthCodeIssueRequest
    ): HttpResponse<Unit> {
        smsAuthenticationService.issue(IssueSmsAuthenticationCommand(authorizationUser.userId, request.phoneNumber))
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @PostMapping("/auth/sms/verify")
    override fun verifySmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: VerifySmsAuthenticationRequest
    ): HttpResponse<VerifySmsAuthenticationResponse> {
        val smsAuthentication = smsAuthenticationService.verify(
            VerifySmsAuthenticationCodeCommand(
                authorizationUser.userId,
                request.phoneNumber,
                request.authCode
            )
        )
        return HttpResponse.of(HttpStatus.OK, VerifySmsAuthenticationResponse(smsAuthentication.isVerified))
    }
}
