package com.official.cufitapi.domain.notification.infrastructure

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.official.cufitapi.domain.notification.domain.PushAlarm
import com.official.cufitapi.domain.notification.domain.PushAlarmClient
import org.springframework.stereotype.Component

@Component
class PushAlarmClientAdapter : PushAlarmClient {

    companion object {
        private const val TITLE = "title"
        private const val BODY = "body"
    }

    override fun sendPushAlarm(pushAlarm: PushAlarm) {
        val message = Message.builder()
            .setToken(pushAlarm.token)
            .putData(TITLE, pushAlarm.title)
            .putData(BODY, pushAlarm.body)
            .build()

        FirebaseMessaging.getInstance().send(message)
    }
}