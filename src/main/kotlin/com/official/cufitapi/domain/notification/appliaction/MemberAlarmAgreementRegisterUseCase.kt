package com.official.cufitapi.domain.notification.appliaction

import com.official.cufitapi.domain.notification.appliaction.command.MemberAlarmAgreeCommand
import com.official.cufitapi.domain.notification.domain.MemberAlarmAgreement

@FunctionalInterface
fun interface MemberAlarmAgreementRegisterUseCase {
    fun agree(memberAlarmAgreeCommand: MemberAlarmAgreeCommand): MemberAlarmAgreement
}