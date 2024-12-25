package com.official.cufitapi.push.api.dto

data class MemberAlarmAgreementRequest(
    val agree: Boolean,
    val alarmType: String
)
