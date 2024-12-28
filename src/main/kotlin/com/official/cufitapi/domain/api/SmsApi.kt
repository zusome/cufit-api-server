package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.dto.auth.SmsAuthValidationRequest
import com.official.cufitapi.domain.application.SmsAuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@ApiV1Controller
class SmsApi(
    private val smsAuthenticationService: SmsAuthenticationService
) {
    @PostMapping("/auth/sms/issue")
    fun issueSmsAuthCode(
        memberId: Long
    ) : ResponseEntity<Any> {
        smsAuthenticationService.issueSmsAuthCode()
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/auth/sms/validation")
    fun validateSmsAuthCode(
        memberId: Long,
        @RequestBody request: SmsAuthValidationRequest
    ) : ResponseEntity<Any> {
        smsAuthenticationService.validateSmsAuthCode(memberId, request)
        return ResponseEntity.noContent().build()
    }

}