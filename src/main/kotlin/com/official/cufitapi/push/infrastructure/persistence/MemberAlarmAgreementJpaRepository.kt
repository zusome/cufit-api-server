package com.official.cufitapi.push.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface MemberAlarmAgreementJpaRepository: JpaRepository<MemberAlarmAgreementEntity, Long>{
    fun findByMemberIdAndAlarmType(memberId: Long, alarmType: String): MemberAlarmAgreementEntity?
}