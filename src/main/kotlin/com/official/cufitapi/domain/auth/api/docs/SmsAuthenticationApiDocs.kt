package com.official.cufitapi.domain.auth.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.auth.api.dto.SmsAuthCodeIssueRequest
import com.official.cufitapi.domain.auth.api.dto.VerifySmsAuthenticationRequest
import com.official.cufitapi.domain.auth.api.dto.VerifySmsAuthenticationResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "휴대폰 인증 관련 API")
interface SmsAuthenticationApiDocs {
    @PostMapping("/auth/sms/issue")
    fun issueSmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: SmsAuthCodeIssueRequest,
    ): HttpResponse<Unit>

    @PostMapping("/auth/sms/verify")
    fun verifySmsAuthCode(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: VerifySmsAuthenticationRequest,
    ): HttpResponse<VerifySmsAuthenticationResponse>
}
