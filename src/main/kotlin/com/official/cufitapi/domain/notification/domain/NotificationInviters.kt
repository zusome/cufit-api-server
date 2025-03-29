package com.official.cufitapi.domain.notification.domain

interface NotificationInviters {
    fun inviterId(inviteeId: Long): Long
}
