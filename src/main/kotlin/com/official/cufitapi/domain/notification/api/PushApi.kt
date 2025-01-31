package com.official.cufitapi.domain.notification.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.docs.PushApiDocs
import com.official.cufitapi.domain.notification.api.dto.DeviceTokenRegisterRequest
import com.official.cufitapi.domain.notification.api.dto.MemberAlarmAgreementRequest
import com.official.cufitapi.domain.notification.api.dto.MemberAlarmAgreementResponse
import com.official.cufitapi.domain.notification.appliaction.DeviceTokenRegisterUseCase
import com.official.cufitapi.domain.notification.appliaction.MemberAlarmAgreementRegisterUseCase
import com.official.cufitapi.domain.notification.appliaction.command.DeviceTokenRegisterCommand
import com.official.cufitapi.domain.notification.appliaction.command.MemberAlarmAgreeCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class PushApi(
    private val memberAlarmAgreementRegisterUseCase: MemberAlarmAgreementRegisterUseCase,
    private val deviceTokenRegisterUseCase: DeviceTokenRegisterUseCase
) : PushApiDocs {

    @PostMapping("/push/agree")
    override fun agree(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: MemberAlarmAgreementRequest
    ): HttpResponse<MemberAlarmAgreementResponse> {
        val memberAlarmAgreement = memberAlarmAgreementRegisterUseCase.agree(
            MemberAlarmAgreeCommand(
                authorizationUser.userId,
                request.agree,
                request.alarmType
            )
        )
        return HttpResponse.of(
            HttpStatus.OK,
            MemberAlarmAgreementResponse(memberAlarmAgreement.agree, memberAlarmAgreement.alarmType)
        )
    }

    @PostMapping("/push/device")
    override fun registerDeviceToken(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: DeviceTokenRegisterRequest
    ): HttpResponse<Unit> {
        deviceTokenRegisterUseCase.registerDeviceToken(
            DeviceTokenRegisterCommand(
                authorizationUser.userId,
                request.platform,
                request.deviceToken
            )
        )
        return HttpResponse.of(HttpStatus.OK, Unit)
    }
}
