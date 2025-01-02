package com.official.cufitapi.domain.notification.domain

import java.time.LocalDateTime

class MemberAlarmAgreement(
    val memberId: Long,
    val agree: Boolean,
    val alarmType: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemberAlarmAgreement

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}