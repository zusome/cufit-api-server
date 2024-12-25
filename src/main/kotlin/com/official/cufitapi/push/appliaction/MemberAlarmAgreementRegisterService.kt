package com.official.cufitapi.push.appliaction

import com.official.cufitapi.push.appliaction.command.MemberAlarmAgreeCommand
import com.official.cufitapi.push.domain.MemberAlarmAgreement
import com.official.cufitapi.push.domain.MemberAlarmAgreementRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberAlarmAgreementRegisterService(
    private val memberAlarmAgreementRepository: MemberAlarmAgreementRepository
): MemberAlarmAgreementRegisterUseCase {

    override fun agree(memberAlarmAgreeCommand: MemberAlarmAgreeCommand): MemberAlarmAgreement =
        memberAlarmAgreementRepository.save(init(memberAlarmAgreeCommand))

    private fun init(memberAlarmAgreeCommand: MemberAlarmAgreeCommand): MemberAlarmAgreement {
        val createdAt = LocalDateTime.now()
        return MemberAlarmAgreement(
            memberId = memberAlarmAgreeCommand.memberId,
            agree = memberAlarmAgreeCommand.agree,
            alarmType = memberAlarmAgreeCommand.alarmType,
            createdAt = createdAt,
            updatedAt = createdAt
        )
    }
}