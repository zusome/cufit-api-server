package com.official.cufitapi.domain.invitation.application.command

import com.official.cufitapi.domain.member.domain.vo.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.domain.vo.MemberType

data class InvitationCardGenerationCommand(
    val memberId: Long,
    val memberType: MemberType,
    val relationType: MatchMakerCandidateRelationType
) {

}