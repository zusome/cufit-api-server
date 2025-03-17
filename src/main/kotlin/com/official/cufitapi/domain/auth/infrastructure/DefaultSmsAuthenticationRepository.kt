package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.repository.SmsAuthenticationRepository
import com.official.cufitapi.domain.auth.infrastructure.persist.JpaSmsAuthentication
import com.official.cufitapi.domain.auth.infrastructure.persist.JpaSmsAuthenticationRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DefaultSmsAuthenticationRepository(
    private val jpaSmsAuthenticationRepository: JpaSmsAuthenticationRepository
): SmsAuthenticationRepository {

    @Transactional(readOnly = false)
    override fun save(smsAuthentication: SmsAuthentication): SmsAuthentication {
        val entity = mapToEntity(smsAuthentication)
        return mapToDomain(jpaSmsAuthenticationRepository.save(entity))
    }

    @Transactional(readOnly = true)
    override fun findByMemberIdOrNull(memberId: Long): SmsAuthentication? {
        return jpaSmsAuthenticationRepository.findByMemberId(memberId)?.let(::mapToDomain)
    }

    private fun mapToDomain(entity: JpaSmsAuthentication): SmsAuthentication = SmsAuthentication(
        phoneNumber = entity.phoneNumber,
        authCode = entity.authCode,
        memberId = entity.memberId,
        isVerified = entity.isVerified,
        id = entity.id
    )

    fun mapToEntity(smsAuthentication: SmsAuthentication): JpaSmsAuthentication = JpaSmsAuthentication(
        phoneNumber = smsAuthentication.phoneNumber,
        authCode = smsAuthentication.authCode,
        memberId = smsAuthentication.memberId,
        isVerified = smsAuthentication.isVerified,
        id = smsAuthentication.id
    )
}
