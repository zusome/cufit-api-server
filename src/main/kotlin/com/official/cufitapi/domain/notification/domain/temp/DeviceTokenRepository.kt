package com.official.cufitapi.domain.notification.domain.temp

interface DeviceTokenRepository {
    fun save(init: DeviceToken): DeviceToken
    fun findByMemberIdAndPlatform(memberId: Long, platform: String): DeviceToken
}
