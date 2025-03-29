package com.official.cufitapi.domain.notification.domain

interface Inviters {
    fun inviterId(inviteeId: Long): Long
}
