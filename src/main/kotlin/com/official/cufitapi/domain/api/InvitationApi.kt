package com.official.cufitapi.domain.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.domain.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeResponse
import com.official.cufitapi.domain.api.dto.invitation.InvitationResponse
import com.official.cufitapi.domain.application.InvitationService
import com.official.cufitapi.domain.application.InvitationTokenGenerationUseCase
import com.official.cufitapi.domain.application.command.InvitationCodeGenerationCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class InvitationApi(
    private val invitationService: InvitationService,
    private val invitationTokenGenerationUseCase: InvitationTokenGenerationUseCase
) : InvitationApiDocs {

    // 초대 코드 검증
    @PostMapping("/invitations/code")
    override fun validateInvitation(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: InvitationCodeRequest
    ): ResponseEntity<InvitationResponse> {
        val response = invitationService.validate(memberId, request)
        return ResponseEntity.ok(response)
    }


    @PostMapping("/invitations")
    override fun generate(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: InvitationCodeGenerateRequest
    ): ResponseEntity<InvitationCodeResponse> {
        val invitationCode = invitationTokenGenerationUseCase.generate(
            InvitationCodeGenerationCommand(
                memberId = memberId,
                memberType = request.memberType,
                relationType = request.relationType
            )
        )
        return ResponseEntity.ok(InvitationCodeResponse(invitationCode))
    }
}