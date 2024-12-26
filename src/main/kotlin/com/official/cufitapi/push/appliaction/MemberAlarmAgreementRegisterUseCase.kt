package com.official.cufitapi.push.appliaction

import com.official.cufitapi.push.appliaction.command.MemberAlarmAgreeCommand
import com.official.cufitapi.push.domain.MemberAlarmAgreement

@FunctionalInterface
fun interface MemberAlarmAgreementRegisterUseCase {
    fun agree(memberAlarmAgreeCommand: MemberAlarmAgreeCommand): MemberAlarmAgreement
}