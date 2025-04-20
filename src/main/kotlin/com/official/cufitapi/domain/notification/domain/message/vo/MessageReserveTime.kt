package com.official.cufitapi.domain.notification.domain.message.vo

import com.official.cufitapi.common.DateTimeUtils
import com.official.cufitapi.common.DateTimeUtils.DEFAULT_DATE_TIME_FORMAT

class MessageReserveTime(
    val reserveTime: String?,
    val reserveTimeZone: String?,
) {
    init {
        if (reserveTime != null && reserveTimeZone == null) {
            throw IllegalArgumentException("Either reserveTime or reserveTimeZone must be set.")
        }
        if (reserveTime == null && reserveTimeZone != null) {
            throw IllegalArgumentException("Either reserveTime or reserveTimeZone must be set.")
        }
        if (DateTimeUtils.isNotFormat(reserveTime!!, DEFAULT_DATE_TIME_FORMAT)) {
            throw IllegalArgumentException("reserveTime format is invalid.")
        }
        if (DateTimeUtils.isAvailableZone(reserveTimeZone!!)) {
            throw IllegalArgumentException("reserveTimeZone format is invalid.")
        }
    }
}
