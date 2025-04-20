package com.official.cufitapi.domain.notification.application.temp

data class MemberAlarmAgreeCommand(
    val memberId: Long,
    val agree: Boolean,
    val alarmType: String
)
