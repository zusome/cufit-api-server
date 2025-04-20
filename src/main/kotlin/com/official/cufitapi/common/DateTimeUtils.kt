package com.official.cufitapi.common

import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

object DateTimeUtils {
    const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"
    private const val ZONE_ID = "Asia/Seoul"

    fun beginToday(): LocalDateTime {
        return ZonedDateTime.now(ZoneId.of(ZONE_ID))
            .truncatedTo(ChronoUnit.DAYS)
            .toLocalDateTime()
    }

    fun isNotFormat(reserveTime: String, format: String): Boolean {
        return try {
            LocalDateTime.parse(reserveTime, DateTimeFormatter.ofPattern(format))
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }

    fun isAvailableZone(reserveTimeZone: String): Boolean {
        return try {
            ZoneId.of(reserveTimeZone)
            true
        } catch (e: DateTimeException) {
            false
        }
    }
}

fun LocalDateTime.tomorrow(): LocalDateTime {
    return this.plusDays(1)
}
