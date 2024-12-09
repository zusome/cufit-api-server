package com.official.cufitapi.domain.api

import com.official.cufitapi.domain.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeResponse
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
    ) : ResponseEntity<Unit> {
        invitationService.validate(memberId, request)
        return ResponseEntity.noContent().build()
    }


    // 초대 코드 생성
    @PostMapping("/invitations")
    fun generate(
        memberId: Long,
        @RequestBody request: InvitationCodeGenerateRequest
    ) : ResponseEntity<InvitationCodeResponse> {
        return ResponseEntity.ok(invitationService.generateInvitationCode(memberId, request))
    }
}