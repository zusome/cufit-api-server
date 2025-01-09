package com.official.cufitapi.domain.member.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationCodeResponse
import com.official.cufitapi.domain.member.api.dto.invitation.InvitationValidationResponse
import com.official.cufitapi.domain.member.application.InvitationTokenGenerationUseCase
import com.official.cufitapi.domain.member.application.InvitationTokenValidationUseCase
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeGenerationCommand
import com.official.cufitapi.domain.member.application.command.invitation.InvitationCodeValidationCommand
import com.official.cufitapi.domain.member.domain.invitation.vo.InvitationCode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class InvitationApi(
    private val invitationTokenGenerationUseCase: InvitationTokenGenerationUseCase,
    private val invitationTokenValidationUseCase: InvitationTokenValidationUseCase
) : InvitationApiDocs {

    // 초대 코드 생성
    @PostMapping("/invitations")
    override fun generate(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: InvitationCodeGenerateRequest
    ): HttpResponse<InvitationCodeResponse> {
        val invitationCode = invitationTokenGenerationUseCase.generate(
            InvitationCodeGenerationCommand(
                memberId = authorizationUser.userId,
                memberType = request.memberType,
                relationType = request.relationType
            )
        )
        return HttpResponse.of(HttpStatus.OK, InvitationCodeResponse(invitationCode))
    }

    // 초대 코드 검증
    @PostMapping("/invitations/code")
    override fun validateInvitation(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: InvitationCodeRequest
    ): HttpResponse<InvitationValidationResponse> {
        val memberType = invitationTokenValidationUseCase.validate(
            InvitationCodeValidationCommand(
                memberId = authorizationUser.userId,
                invitationCode = InvitationCode(code = request.invitationCode)
            )
        )
        return HttpResponse.of(HttpStatus.OK, InvitationValidationResponse(memberType))
    }
}