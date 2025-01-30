package com.official.cufitapi.domain.member.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.MemberTypeInfo
import com.official.cufitapi.domain.member.api.dto.UpdateMemberProfileRequest
import com.official.cufitapi.domain.member.infrastructure.persistence.dto.MemberInfoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "사용자 관련 API")
interface MemberApiDocs {

    @Operation(
        summary = "사용자 타입 조회 API (일반/주선자/후보자)"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun memberType(
        @Authorization(
            AuthorizationType.BASIC, AuthorizationType.CANDIDATE, AuthorizationType.MATCHMAKER
        ) authorizationUser: AuthorizationUser,
    ): HttpResponse<MemberTypeInfo>

    @Operation(
        summary = "사용자 정보 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun relationInfo(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
    ): HttpResponse<MemberInfoResponse>

    @Operation(
        summary = "사용자 실명 정보 반영 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    @PostMapping("/members/profile")
    fun realName(
        @Authorization(AuthorizationType.BASIC) authorizationUser: AuthorizationUser,
        @RequestBody request: UpdateMemberProfileRequest,
    ): HttpResponse<Unit>
}
