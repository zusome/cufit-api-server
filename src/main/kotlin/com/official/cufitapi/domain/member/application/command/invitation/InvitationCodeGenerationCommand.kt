package com.official.cufitapi.domain.member.application.command.invitation

import com.official.cufitapi.domain.member.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.member.enums.MemberType

data class InvitationCodeGenerationCommand(
    val memberId: Long,
    val memberType: MemberType,
    val relationType: MatchMakerCandidateRelationType
) {

}