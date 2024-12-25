package com.official.cufitapi.push.domain

interface MemberAlarmAgreementRepository {
    fun save(memberAlarmAgreement: MemberAlarmAgreement): MemberAlarmAgreement
}