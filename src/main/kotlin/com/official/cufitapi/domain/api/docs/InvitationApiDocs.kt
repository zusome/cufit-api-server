package com.official.cufitapi.domain.api.docs

import com.official.cufitapi.common.api.HttpResponse
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeGenerateRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeRequest
import com.official.cufitapi.domain.api.dto.invitation.InvitationCodeResponse
import com.official.cufitapi.domain.api.dto.invitation.InvitationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.RequestBody


@Tag(name = "초대 관련 API")
interface InvitationApiDocs {

    @Operation(
        summary = "초대코드 검증 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun validateInvitation(
        memberId: Long,
        @RequestBody request: InvitationCodeRequest
    ) : HttpResponse<InvitationResponse>


    @Operation(
        summary = "초대코드 생성 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun generate(
        memberId: Long,
        @RequestBody request: InvitationCodeGenerateRequest
    ) : HttpResponse<InvitationCodeResponse>
}