package com.official.cufitapi.domain.notification.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.dto.NotificationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag


@Tag(name = "알림 관련 API")
interface NotificationApiDocs {

    @Operation(
        summary = "알림 목록 조회 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun getNotificationList(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser
    ): HttpResponse<List<NotificationResponse>>
}