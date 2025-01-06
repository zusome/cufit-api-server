package com.official.cufitapi.domain.notification.domain

interface MemberAlarmAgreementRepository {
    fun save(memberAlarmAgreement: MemberAlarmAgreement): MemberAlarmAgreement
}