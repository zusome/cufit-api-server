package com.official.cufitapi.domain.member.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.CandidateProfileInfoResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateMatchBreakResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateMatchResultResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidatePresignedUrlUploadResponse
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateProfileUpdateRequest
import com.official.cufitapi.domain.member.api.dto.candidate.CandidateMatchSuggestionResponse
import com.official.cufitapi.domain.member.api.dto.candidate.MatchBreakRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "후보자 관련 API")
interface CandidateApiDocs {

    @Operation(
        summary = "주선자로부터 요청 받은 후보자 목록 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getSuggestedCandidate(
        @Authorization(AuthorizationType.CANDIDATE) authorizationUser: AuthorizationUser,
    ): HttpResponse<List<CandidateMatchSuggestionResponse>>

    @Operation(
        summary = "후보자 프로필 업데이트 API",
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun updateCandidateBasicProfile(
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
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: MatchBreakRequest,
    ): HttpResponse<Unit>

    @Operation(
        summary = "후보자 프로필 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun profile(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateProfileInfoResponse>

    @Operation(
        summary = "후보자 프로필 업로드 presigned url 발급 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getProfileImageUploadPresignedUrl(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidatePresignedUrlUploadResponse>

    @Operation(
        summary = "승인된 후보자 목록 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getSuggestedResultCandidate(authorizationUser: AuthorizationUser): HttpResponse<List<CandidateMatchResultResponse>>


    @Operation(
        summary = "매칭 쉬어가기 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findBreakMatching(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<CandidateMatchBreakResponse>
}
