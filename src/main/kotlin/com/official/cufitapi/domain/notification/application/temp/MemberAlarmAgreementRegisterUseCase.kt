package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.application.command.MemberAlarmAgreeCommand
import com.official.cufitapi.domain.notification.domain.temp.MemberAlarmAgreement

@FunctionalInterface
fun interface MemberAlarmAgreementRegisterUseCase {
    fun agree(memberAlarmAgreeCommand: MemberAlarmAgreeCommand): MemberAlarmAgreement
}
