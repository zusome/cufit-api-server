package com.official.cufitapi.domain.auth.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.docs.AuthApiDocs
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.auth.SmsAuthValidationRequest
import com.official.cufitapi.domain.member.application.SmsAuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class SmsAuthorizationApi(
    private val smsAuthenticationService: SmsAuthenticationService
) : AuthApiDocs {

    @PostMapping("/auth/sms/issue")
    fun issueSmsAuthCode(
        @Authorization(AuthorizationType.ALL) memberId: Long
    ): HttpResponse<Unit> {
        smsAuthenticationService.issueSmsAuthCode()
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }

    @PostMapping("/auth/sms/validation")
    fun validateSmsAuthCode(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: SmsAuthValidationRequest
    ): HttpResponse<Unit> {
        smsAuthenticationService.validateSmsAuthCode(memberId, request)
        return HttpResponse.of(HttpStatus.NO_CONTENT, Unit)
    }
}
