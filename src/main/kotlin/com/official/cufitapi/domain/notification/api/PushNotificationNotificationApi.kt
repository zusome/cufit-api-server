package com.official.cufitapi.domain.notification.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.common.api.ApiV1Controller
import com.official.cufitapi.common.api.dto.HttpResponse
import com.official.cufitapi.domain.notification.api.docs.PushNotificationApiDocs
import com.official.cufitapi.domain.notification.api.dto.RegisterDeviceTokenRequest
import com.official.cufitapi.domain.notification.api.dto.AgreeMemberAlarmRequest
import com.official.cufitapi.domain.notification.api.dto.AgreeMemberAlarmResponse
import com.official.cufitapi.domain.notification.application.temp.DeviceTokenRegisterUseCase
import com.official.cufitapi.domain.notification.application.temp.MemberAlarmAgreementRegisterUseCase
import com.official.cufitapi.domain.notification.application.command.DeviceTokenRegisterCommand
import com.official.cufitapi.domain.notification.application.command.MemberAlarmAgreeCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class PushNotificationNotificationApi(
    private val memberAlarmAgreementRegisterUseCase: MemberAlarmAgreementRegisterUseCase,
    private val deviceTokenRegisterUseCase: DeviceTokenRegisterUseCase
) : PushNotificationApiDocs {

    @PostMapping("/push/agree")
    override fun agree(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: AgreeMemberAlarmRequest
    ): HttpResponse<AgreeMemberAlarmResponse> {
        val memberAlarmAgreement = memberAlarmAgreementRegisterUseCase.agree(
            MemberAlarmAgreeCommand(
                authorizationUser.userId,
                request.agree,
                request.alarmType
            )
        )
        return HttpResponse.of(
            HttpStatus.OK,
            AgreeMemberAlarmResponse(memberAlarmAgreement.agree, memberAlarmAgreement.alarmType)
        )
    }

    @PostMapping("/push/device")
    override fun registerDeviceToken(
        @Authorization(AuthorizationType.ALL) authorizationUser: AuthorizationUser,
        @RequestBody request: RegisterDeviceTokenRequest
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
