package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.common.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeResponse
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationResponse
import com.official.cufitapi.domain.member.application.InvitationTokenGenerationUseCase
import com.official.cufitapi.domain.member.application.InvitationTokenValidationUseCase
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeGenerationCommand
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeValidationCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class InvitationApi(
    private val invitationTokenGenerationUseCase: InvitationTokenGenerationUseCase,
    private val invitationTokenValidationUseCase: InvitationTokenValidationUseCase
) : InvitationApiDocs {

    // 초대 코드 검증
    @PostMapping("/invitations/code")
    override fun validateInvitation(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: InvitationCodeRequest
    ): HttpResponse<InvitationResponse> {
        val inviteeName = invitationTokenValidationUseCase.validate(
            InvitationCodeValidationCommand(
                memberId = memberId,
                invitationCode = com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode(code = request.invitationCode)
            )
        )
        return HttpResponse.of(HttpStatus.OK, InvitationResponse(inviteeName))
    }

    @PostMapping("/invitations")
    override fun generate(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: InvitationCodeGenerateRequest
    ): HttpResponse<InvitationCodeResponse> {
        val invitationCode = invitationTokenGenerationUseCase.generate(
            InvitationCodeGenerationCommand(
                memberId = memberId,
                memberType = request.memberType,
                relationType = request.relationType
            )
        )
        return HttpResponse.of(HttpStatus.OK, InvitationCodeResponse(invitationCode))
    }
}