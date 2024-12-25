package com.official.cufitapi.push.infrastructure

import com.official.cufitapi.push.domain.DeviceToken
import com.official.cufitapi.push.domain.DeviceTokenRepository
import com.official.cufitapi.push.infrastructure.persistence.DeviceTokenEntity
import com.official.cufitapi.push.infrastructure.persistence.DeviceTokenJpaRepository
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
