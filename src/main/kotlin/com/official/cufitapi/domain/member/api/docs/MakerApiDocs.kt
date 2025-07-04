package com.official.cufitapi.domain.member.api.docs

import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.maker.CandidateCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerCandidatesResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesCountResponse
import com.official.cufitapi.domain.member.api.dto.maker.MakerOtherCandidatesResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "주선자 관련 API")
interface MakerApiDocs {

    @Operation(
        summary = "내 후보자 수 조회 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findCandidatesCount(authorizationUser: AuthorizationUser): HttpResponse<CandidateCountResponse>

    @Operation(
        summary = "상대 후보자 수 조회 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findOtherCandidatesCount(authorizationUser: AuthorizationUser): HttpResponse<MakerOtherCandidatesCountResponse>

    @Operation(
        summary = "내 후보자 목록 조회 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findCandidates(authorizationUser: AuthorizationUser): HttpResponse<MakerCandidatesResponse>

    @Operation(
        summary = "상대 후보자 목록 조회 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findOtherCandidates(authorizationUser: AuthorizationUser): HttpResponse<MakerOtherCandidatesResponse>
}
