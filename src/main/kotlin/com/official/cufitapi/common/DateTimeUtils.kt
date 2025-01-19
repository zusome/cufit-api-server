package com.official.cufitapi.common

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

object DateTimeUtils {
    private const val ZONE_ID = "Asia/Seoul"

    fun beginToday(): LocalDateTime {
        return ZonedDateTime.now(ZoneId.of(ZONE_ID))
            .truncatedTo(ChronoUnit.DAYS)
            .toLocalDateTime()
    }
}

fun LocalDateTime.tomorrow(): LocalDateTime {
    return this.plusDays(1)
}
