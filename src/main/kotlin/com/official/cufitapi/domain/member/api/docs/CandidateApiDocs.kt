package com.official.cufitapi.domain.member.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.CandidateProfileInfoResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateProfileUpdateRequest
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateResponse
import com.official.cufitapi.domain.member.api.dto.candidate.MatchBreakRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "후보자 관련 API")
interface CandidateApiDocs {

    @Operation(
        summary = "제안된 후보자 목록 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getSuggestedCandidate(
        @Authorization(AuthorizationType.CANDIDATE) authorizationUser: AuthorizationUser,
    ): HttpResponse<List<CandidateResponse>>

    @Operation(
        summary = "후보자 프로필 업데이트 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun updateCandidateProfile(
        authorizationUser: AuthorizationUser,
        request: CandidateProfileUpdateRequest,
    ): HttpResponse<Unit>

    @Operation(
        summary = "매칭 쉬어가기 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun breakMatching(
        @Authorization(AuthorizationType.CANDIDATE) authorizationUser: AuthorizationUser,
        @RequestBody request: MatchBreakRequest,
    ): HttpResponse<Unit>

    @GetMapping("/members/profiles")
    fun profile(
        @Authorization(AuthorizationType.CANDIDATE) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateProfileInfoResponse>
}
