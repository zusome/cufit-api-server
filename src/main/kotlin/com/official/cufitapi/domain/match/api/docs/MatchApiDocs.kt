package com.official.cufitapi.domain.match.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.match.api.dto.SuggestMatchRequest
import com.official.cufitapi.domain.match.api.dto.SuggestMatchResponse
import com.official.cufitapi.domain.match.api.dto.UpdateMatchRequest
import com.official.cufitapi.domain.match.infrastructure.persistence.MatchDao
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "주선 관련 API")
interface MatchApiDocs {

    @Operation(
        summary = "주선 제안"
    )
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun suggest(
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: SuggestMatchRequest,
    ): HttpResponse<SuggestMatchResponse>

    @Operation(summary = "주선 수락/거절 API")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun updateMatch(
        @PathVariable("matchId") matchId: Long,
        @Authorization(
            AuthorizationType.BASIC,
            AuthorizationType.CANDIDATE
        ) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateMatchRequest,
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
            AuthorizationType.MAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MatchDao.MatchCandidates>
}
