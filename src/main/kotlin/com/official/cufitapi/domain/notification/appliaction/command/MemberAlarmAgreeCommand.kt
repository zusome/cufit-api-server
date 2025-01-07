package com.official.cufitapi.domain.notification.appliaction.command

data class MemberAlarmAgreeCommand(
    val memberId: Long,
    val agree: Boolean,
    val alarmType: String
)