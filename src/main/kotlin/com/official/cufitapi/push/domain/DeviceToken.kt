package com.official.cufitapi.push.domain

import java.time.LocalDateTime

class DeviceToken(
    val memberId: Long,
    val platform: String,
    val deviceToken: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val id: Long? = null
) {
}