package com.official.cufitapi.domain.notification.application.command

data class MemberAlarmAgreeCommand(
    val memberId: Long,
    val agree: Boolean,
    val alarmType: String
)
