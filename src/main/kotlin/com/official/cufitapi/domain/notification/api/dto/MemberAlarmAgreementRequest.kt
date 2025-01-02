package com.official.cufitapi.domain.notification.api.dto

data class MemberAlarmAgreementRequest(
    val agree: Boolean,
    val alarmType: String
)
