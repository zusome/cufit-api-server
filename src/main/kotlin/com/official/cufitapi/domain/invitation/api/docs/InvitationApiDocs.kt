package com.official.cufitapi.domain.invitation.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.invitation.api.dto.AcceptInvitationCardResponse
import com.official.cufitapi.domain.invitation.api.dto.GenerateInvitationCardRequest
import com.official.cufitapi.domain.invitation.api.dto.AcceptInvitationCardRequest
import com.official.cufitapi.domain.invitation.api.dto.GenerateInvitationCardResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "초대 관련 API")
interface InvitationApiDocs {

    @Operation(
        summary = "초대코드 검증 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun accept(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: AcceptInvitationCardRequest
    ) : HttpResponse<AcceptInvitationCardResponse>


    @Operation(
        summary = "초대코드 생성 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun generate(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: GenerateInvitationCardRequest
    ) : HttpResponse<GenerateInvitationCardResponse>
}
