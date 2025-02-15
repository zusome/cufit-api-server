package com.official.cufitapi.domain.notification.domain

interface PushAlarmClient {
    fun sendPushAlarm(pushAlarm: PushAlarm)
}