package com.official.cufitapi.domain.notification.domain.temp

interface MemberAlarmAgreementRepository {
    fun save(memberAlarmAgreement: MemberAlarmAgreement): MemberAlarmAgreement
}
