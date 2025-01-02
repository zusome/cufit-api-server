package com.official.cufitapi.domain.notification.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberAlarmAgreementJpaRepository: JpaRepository<MemberAlarmAgreementEntity, Long>{
    fun findByMemberIdAndAlarmType(memberId: Long, alarmType: String): MemberAlarmAgreementEntity?
}