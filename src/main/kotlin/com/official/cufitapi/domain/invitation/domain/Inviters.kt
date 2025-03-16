package com.official.cufitapi.domain.invitation.domain

import com.official.cufitapi.domain.invitation.domain.vo.Inviter

interface Inviters {
    fun findById(inviterId: Long): Inviter
    fun findByIdOrNull(inviterId: Long): Inviter?
}
