package com.official.cufitapi.domain.notification.application.temp

import com.official.cufitapi.domain.notification.application.command.MemberAlarmAgreeCommand
import com.official.cufitapi.domain.notification.domain.temp.MemberAlarmAgreement
import com.official.cufitapi.domain.notification.domain.temp.MemberAlarmAgreementRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MemberAlarmAgreementRegisterService(
    private val memberAlarmAgreementRepository: MemberAlarmAgreementRepository
) : MemberAlarmAgreementRegisterUseCase {

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
