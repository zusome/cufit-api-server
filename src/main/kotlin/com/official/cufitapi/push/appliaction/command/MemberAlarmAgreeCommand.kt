package com.official.cufitapi.push.appliaction.command

data class MemberAlarmAgreeCommand(
    val memberId: Long,
    val agree: Boolean,
    val alarmType: String
)