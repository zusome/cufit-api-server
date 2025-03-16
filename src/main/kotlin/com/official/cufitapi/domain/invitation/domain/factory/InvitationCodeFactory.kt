package com.official.cufitapi.domain.invitation.domain.factory

import com.official.cufitapi.domain.invitation.domain.vo.InvitationCode
import com.official.cufitapi.domain.invitation.domain.vo.InvitationRelationType
import com.official.cufitapi.domain.invitation.domain.vo.InvitationType

interface InvitationCodeFactory {
    fun generate(invitationType: InvitationType, invitationRelationType: InvitationRelationType): InvitationCode
}

