package com.official.cufitapi.domain.notification.api.docs

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.dto.RegisterDeviceTokenRequest
import com.official.cufitapi.domain.notification.api.dto.AgreeMemberAlarmRequest
import com.official.cufitapi.domain.notification.api.dto.AgreeMemberAlarmResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "푸시 알림 관련 API")
interface PushNotificationApiDocs {

    @Operation(
        summary = "푸시 알림 수신 동의 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun agree(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: AgreeMemberAlarmRequest
    ): HttpResponse<AgreeMemberAlarmResponse>


    @Operation(
        summary = "디바이스 토큰 등록 API"
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공"),
        ApiResponse(responseCode = "401", description = "인증 실패"),
        ApiResponse(responseCode = "500", description = "서버 에러")
    )
    fun registerDeviceToken(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: RegisterDeviceTokenRequest
    ): HttpResponse<Unit>


}
