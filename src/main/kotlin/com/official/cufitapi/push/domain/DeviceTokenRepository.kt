package com.official.cufitapi.push.domain

interface DeviceTokenRepository {
    fun save(init: DeviceToken): DeviceToken
}