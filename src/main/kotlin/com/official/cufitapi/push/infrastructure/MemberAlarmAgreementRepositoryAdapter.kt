package com.official.cufitapi.push.infrastructure

import com.official.cufitapi.push.domain.MemberAlarmAgreement
import com.official.cufitapi.push.domain.MemberAlarmAgreementRepository
import com.official.cufitapi.push.infrastructure.persistence.MemberAlarmAgreementEntity
import com.official.cufitapi.push.infrastructure.persistence.MemberAlarmAgreementJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberAlarmAgreementRepositoryAdapter(
    private val memberAlarmAgreementJpaRepository: MemberAlarmAgreementJpaRepository
) : MemberAlarmAgreementRepository {

    @Transactional(rollbackFor = [Exception::class])
    override fun save(memberAlarmAgreement: MemberAlarmAgreement): MemberAlarmAgreement {
        val entity = (memberAlarmAgreementJpaRepository.findByMemberIdAndAlarmType(
            memberAlarmAgreement.memberId,
            memberAlarmAgreement.alarmType
        )?.let {
            it.agree = memberAlarmAgreement.agree
            it.updatedAt = memberAlarmAgreement.updatedAt
            memberAlarmAgreementJpaRepository.save(it)
        } ?: memberAlarmAgreementJpaRepository.save(mapToEntity(memberAlarmAgreement)))
        return mapToDomain(entity)
    }

    private fun mapToDomain(entity: MemberAlarmAgreementEntity): MemberAlarmAgreement =
        MemberAlarmAgreement(
            memberId = entity.memberId,
            agree = entity.agree,
            alarmType = entity.alarmType,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            id = entity.id
        )

    private fun mapToEntity(memberAlarmAgreement: MemberAlarmAgreement): MemberAlarmAgreementEntity =
        MemberAlarmAgreementEntity(
            memberId = memberAlarmAgreement.memberId,
            agree = memberAlarmAgreement.agree,
            alarmType = memberAlarmAgreement.alarmType,
            createdAt = memberAlarmAgreement.createdAt,
            updatedAt = memberAlarmAgreement.updatedAt,
            id = memberAlarmAgreement.id
        )
}