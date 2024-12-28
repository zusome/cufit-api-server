package com.official.cufitapi.domain.application.command

import com.official.cufitapi.domain.enums.MatchMakerCandidateRelationType
import com.official.cufitapi.domain.enums.MemberType

data class InvitationCodeGenerationCommand(
    val memberId: Long,
    val memberType: MemberType,
    val relationType: MatchMakerCandidateRelationType
) {

}