package com.official.cufitapi.domain.invitation.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.invitation.api.docs.InvitationApiDocs
import com.official.cufitapi.domain.invitation.api.dto.AcceptInvitationCardRequest
import com.official.cufitapi.domain.invitation.api.dto.AcceptInvitationCardResponse
import com.official.cufitapi.domain.invitation.api.dto.GenerateInvitationCardRequest
import com.official.cufitapi.domain.invitation.api.dto.GenerateInvitationCardResponse
import com.official.cufitapi.domain.invitation.application.AcceptInvitationCardUseCase
import com.official.cufitapi.domain.invitation.application.FindInvitersUseCase
import com.official.cufitapi.domain.invitation.application.GenerateInvitationCardUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class InvitationApi(
    private val generateInvitationCardUseCase: GenerateInvitationCardUseCase,
    private val acceptInvitationCardUseCase: AcceptInvitationCardUseCase,
    private val findInvitersUseCase: FindInvitersUseCase,
) : InvitationApiDocs {

    @PostMapping("/invitations")
    override fun generate(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: GenerateInvitationCardRequest,
    ): HttpResponse<GenerateInvitationCardResponse> {
        val invitationCard = generateInvitationCardUseCase.generate(request.toCommand(authorizationUser.userId))
        return HttpResponse.of(HttpStatus.OK, GenerateInvitationCardResponse(invitationCard))
    }

    @PostMapping("/invitations/code")
    override fun accept(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: AcceptInvitationCardRequest,
    ): HttpResponse<AcceptInvitationCardResponse> {
        val invitationCard = acceptInvitationCardUseCase.accept(request.toCommand(authorizationUser.userId))
        val inviter = findInvitersUseCase.findByInviterId(invitationCard.inviterId)
        return HttpResponse.of(
            HttpStatus.OK,
            AcceptInvitationCardResponse(invitationCard.invitationType.value, inviter.name)
        )
    }
}
