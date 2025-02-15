package com.official.cufitapi.domain.arrangement.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.arrangement.api.dto.SuggestArrangementRequest
import com.official.cufitapi.domain.arrangement.api.dto.SuggestArrangementResponse
import com.official.cufitapi.domain.arrangement.api.dto.UpdateArrangementRequest
import com.official.cufitapi.domain.arrangement.infrastructure.persistence.ArrangementDao
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "주선 관련 API")
interface ArrangementApiDocs {

    @Operation(
        summary = "주선 제안"
    )
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun suggestArrangement(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestArrangementRequest,
    ): HttpResponse<SuggestArrangementResponse>

    @Operation(summary = "주선 수락/거절 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun updateArrangement(
        @PathVariable("arrangementId") arrangementId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateArrangementRequest,
    ): HttpResponse<Void>

    @Operation(
        summary = "사용 가능한 후보자 조회"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun findAvailableCandidates(
        @RequestParam("targetId") targetId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<ArrangementDao.ArrangementCandidates>
}