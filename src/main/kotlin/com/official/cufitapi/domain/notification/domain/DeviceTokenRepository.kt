package com.official.cufitapi.domain.notification.domain

interface DeviceTokenRepository {
    fun save(init: DeviceToken): DeviceToken
}