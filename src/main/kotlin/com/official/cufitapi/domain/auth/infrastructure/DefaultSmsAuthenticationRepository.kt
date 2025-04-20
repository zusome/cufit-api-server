package com.official.cufitapi.domain.auth.infrastructure

import com.official.cufitapi.domain.auth.domain.sms.SmsAuthentication
import com.official.cufitapi.domain.auth.domain.sms.SmsAuthenticationRepository
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
        phone = entity.phone,
        code = entity.code,
        memberId = entity.memberId,
        isVerified = entity.isVerified,
        id = entity.id
    )

    fun mapToEntity(smsAuthentication: SmsAuthentication): JpaSmsAuthentication = JpaSmsAuthentication(
        phone = smsAuthentication.phone,
        code = smsAuthentication.code,
        memberId = smsAuthentication.memberId,
        isVerified = smsAuthentication.isVerified,
        id = smsAuthentication.id
    )
}
