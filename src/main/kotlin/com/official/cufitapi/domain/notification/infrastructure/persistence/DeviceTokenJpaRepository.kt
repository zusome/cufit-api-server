package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenJpaRepository: JpaRepository<DeviceTokenEntity, Long> {
    fun findByMemberIdAndPlatform(memberId: Long, platform: String): DeviceTokenEntity?
}