package com.official.cufitapi.domain.notification.domain

interface DeviceTokenRepository {
    fun save(init: DeviceToken): DeviceToken
    fun findByMemberIdAndPlatform(memberId: Long, platform: String): DeviceToken
}