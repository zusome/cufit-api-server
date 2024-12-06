package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.api.dto.InvitationCodeRequest
import com.official.cufitapi.domain.application.InvitationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class InvitationApiController(
    private val invitationService: InvitationService
) : InvitationApiDocs {

    // 초대 코드 검증
    @PostMapping("/invitations/code")
    fun validateInvitation(
        memberId: Long,
        @RequestBody request: InvitationCodeRequest
    ) {
        invitationService.validate(memberId, request)
    }


    // 초대 코드 생성
    @PostMapping("/invitations")
    fun generate(
        memberId: Long
    ) {
        invitationService.generateInvitationCode(memberId)
    }
}