package com.official.cufitapi.push.api

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationType
import com.official.cufitapi.domain.api.ApiV1Controller
import com.official.cufitapi.push.api.dto.DeviceTokenRegisterRequest
import com.official.cufitapi.push.api.dto.MemberAlarmAgreementRequest
import com.official.cufitapi.push.api.dto.MemberAlarmAgreementResponse
import com.official.cufitapi.push.appliaction.DeviceTokenRegisterUseCase
import com.official.cufitapi.push.appliaction.MemberAlarmAgreementRegisterUseCase
import com.official.cufitapi.push.appliaction.command.DeviceTokenRegisterCommand
import com.official.cufitapi.push.appliaction.command.MemberAlarmAgreeCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@ApiV1Controller
class PushApi(
    private val memberAlarmAgreementRegisterUseCase: MemberAlarmAgreementRegisterUseCase,
    private val deviceTokenRegisterUseCase: DeviceTokenRegisterUseCase
) {

    @PostMapping("/push/agree")
    fun agree(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: MemberAlarmAgreementRequest
    ): ResponseEntity<MemberAlarmAgreementResponse> {
        val memberAlarmAgreement = memberAlarmAgreementRegisterUseCase.agree(
            MemberAlarmAgreeCommand(
                memberId,
                request.agree,
                request.alarmType
            )
        )
        return ResponseEntity.ok()
            .body(MemberAlarmAgreementResponse(memberAlarmAgreement.agree, memberAlarmAgreement.alarmType))
    }

    @PostMapping("/push/device")
    fun registerDeviceToken(
        @Authorization(AuthorizationType.ALL) memberId: Long,
        @RequestBody request: DeviceTokenRegisterRequest
    ): ResponseEntity<Unit> {
        deviceTokenRegisterUseCase.registerDeviceToken(
            DeviceTokenRegisterCommand(
                memberId,
                request.platform,
                request.deviceToken
            )
        )
        return ResponseEntity.ok().build()
    }
}
