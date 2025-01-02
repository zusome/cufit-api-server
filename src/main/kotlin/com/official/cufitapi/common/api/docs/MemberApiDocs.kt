package com.official.cufitapi.common.api.docs

import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.member.api.dto.MemberTypeInfoResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "사용자 관련 API")
interface MemberApiDocs {

    @Operation(
        summary = "사용자 타입 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getMemberTypeInfo(memberId: Long): HttpResponse<MemberTypeInfoResponse>
}