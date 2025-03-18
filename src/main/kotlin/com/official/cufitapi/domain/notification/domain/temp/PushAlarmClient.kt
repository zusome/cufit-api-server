package com.official.cufitapi.domain.notification.domain.temp

interface PushAlarmClient {
    fun sendPushAlarm(pushAlarm: PushAlarm)
}
