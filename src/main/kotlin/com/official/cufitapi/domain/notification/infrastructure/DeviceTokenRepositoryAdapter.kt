package com.official.cufitapi.domain.notification.infrastructure

import com.official.cufitapi.common.config.ErrorCode
import com.official.cufitapi.common.exception.NotFoundException
import com.official.cufitapi.domain.notification.domain.DeviceToken
import com.official.cufitapi.domain.notification.domain.DeviceTokenRepository
import com.official.cufitapi.domain.notification.infrastructure.persistence.DeviceTokenEntity
import com.official.cufitapi.domain.notification.infrastructure.persistence.DeviceTokenJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DeviceTokenRepositoryAdapter(
    private val deviceTokenJpaRepository: DeviceTokenJpaRepository
) : DeviceTokenRepository {

    @Transactional(rollbackFor = [Exception::class])
    override fun save(deviceToken: DeviceToken): DeviceToken {
        val entity = deviceTokenJpaRepository.findByMemberIdAndPlatform(deviceToken.memberId, deviceToken.platform)
            ?.let {
                it.deviceToken = deviceToken.deviceToken
                deviceTokenJpaRepository.save(it)
            } ?: deviceTokenJpaRepository.save(mapToEntity(deviceToken))
        return mapToDomain(entity)
    }

    override fun findByMemberIdAndPlatform(memberId: Long, platform: String): DeviceToken {
        val entity = deviceTokenJpaRepository.findByMemberIdAndPlatform(memberId, platform)
            ?: throw NotFoundException(ErrorCode.NOT_FOUND_DEVICE_TOKEN_NOT_FOUND)
        return mapToDomain(entity)
    }

    private fun mapToEntity(deviceToken: DeviceToken): DeviceTokenEntity =
        DeviceTokenEntity(
            id = deviceToken.id,
            memberId = deviceToken.memberId,
            platform = deviceToken.platform,
            deviceToken = deviceToken.deviceToken,
            createdAt = deviceToken.createdAt,
            updatedAt = deviceToken.updatedAt,
        )

    private fun mapToDomain(deviceTokenEntity: DeviceTokenEntity): DeviceToken =
        DeviceToken(
            id = deviceTokenEntity.id,
            memberId = deviceTokenEntity.memberId,
            platform = deviceTokenEntity.platform,
            deviceToken = deviceTokenEntity.deviceToken,
            createdAt = deviceTokenEntity.createdAt,
            updatedAt = deviceTokenEntity.updatedAt,
        )
}
